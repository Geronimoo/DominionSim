package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.Collections;

public class ReplaceCard extends DomCard {
    public ReplaceCard() {
      super( DomCardName.Replace);
    }

    public void play() {
      if (owner.getCardsInHand().isEmpty())
    	return;
      DomCard theCardToTrash = owner.findCardToRemodel(this, 2);
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

    private void distributeCurses() {
        for (DomPlayer player : owner.getOpponents()) {
            if (player.checkDefense())
                continue;
            player.gain(DomCardName.Curse);
        }
    }

    @Override
    public boolean wantsToBePlayed() {
      return owner.findCardToRemodel(this, 2)!=null;
   }
}