package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class ReplaceCard extends DomCard {
    public ReplaceCard() {
      super( DomCardName.Replace);
    }

    public void play() {
      if (owner.getCardsInHand().isEmpty())
    	return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHumanPlayer();
          return;
      }

      DomCard theCardToTrash = owner.findCardToRemodel(this, 2, true);
      if (theCardToTrash==null) {
        //this is needed when card is played with Throne Room effect
        Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
        theCardToTrash=owner.getCardsInHand().get(0);
      }
      owner.trash(owner.removeCardFromHand( theCardToTrash));
      DomCost theMaxCostOfCardToGain = new DomCost( theCardToTrash.getCoinCost(owner.getCurrentGame()) + 2, theCardToTrash.getPotionCost());
	  DomCardName theDesiredCard = owner.getDesiredCard(theMaxCostOfCardToGain, false);
      if (theDesiredCard==null)
    	theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theMaxCostOfCardToGain);
      if (theDesiredCard!=null) {
          if (theDesiredCard.hasCardType(DomCardType.Action) || theDesiredCard.hasCardType(DomCardType.Treasure)) {
              owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(theDesiredCard));
          }else {
              owner.gain(theDesiredCard);
              if (theDesiredCard.hasCardType(DomCardType.Victory))
                  distributeCurses();
          }
      }
    }

    private void handleHumanPlayer() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand())
            theChooseFrom.add(theCard.getName());
        DomCard theCardToReplace = owner.getCardsFromHand(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to " + this.getName().toString(), theChooseFrom, "Mandatory!")).get(0);
        owner.trash(owner.removeCardFromHand(theCardToReplace));
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theCardToReplace.getCost(owner.getCurrentGame()).add(new DomCost(2,0)).customCompare(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain from " + this.getName().toString(), theChooseFrom, "Mandatory!");
        if (theChosenCard.hasCardType(DomCardType.Victory)) {
            for (DomPlayer player : owner.getOpponents()) {
                if (player.checkDefense())
                    continue;
                player.gain(DomCardName.Curse);
            }
        }
       if (theChosenCard.hasCardType(DomCardType.Treasure)||theChosenCard.hasCardType(DomCardType.Action))
         owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(theChosenCard));
       else
         owner.gain(theChosenCard);
    }

    private void distributeCurses() {
        for (DomPlayer player : owner.getOpponents()) {
            if (player.checkDefense())
                continue;
            player.gain(DomCardName.Curse);
        }
    }

    @Override
    public boolean wantsToBePlayed() {
      return owner.findCardToRemodel(this, 2, true)!=null;
   }
}