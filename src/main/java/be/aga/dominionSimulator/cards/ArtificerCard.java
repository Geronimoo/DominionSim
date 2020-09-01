package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class ArtificerCard extends DomCard {
    public ArtificerCard() {
      super( DomCardName.Artificer);
    }

    public void play() {
      owner.addActions(1);
      owner.addAvailableCoins(1);
      owner.drawCards(1);
      if (owner.getCardsInHand().isEmpty())
          return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      Collections.sort(owner.getCardsInHand(),SORT_FOR_DISCARD_FROM_HAND);
      int i=0;
      DomCard theNextCard = owner.getCardsInHand().get(i);
      while (theNextCard.getDiscardPriority(owner.getActionsAndVillagersLeft())<=DomCardName.Copper.getDiscardPriority(1) && i<owner.getCardsInHand().size()-1)
          theNextCard= owner.getCardsInHand().get(++i);
      DomCardName theDesiredCard = null;
      int j = 0;
      if (i>0) {
          for (j=i;j>0;j--) {
              theDesiredCard = owner.getDesiredCard(new DomCost( j, 0), true);
              if (theDesiredCard!=null)
                  break;
          }
      }
      if (theDesiredCard==null)
          return;

      DomCardName theDesiredCardForDiscardingAll = owner.getDesiredCard(new DomCost(owner.getCardsInHand().size(), 0), false);
      DomCardName theCardToBeBought = owner.getDesiredCard(owner.getTotalPotentialCurrency(), false);
      if (theDesiredCardForDiscardingAll!=null && (theCardToBeBought==null || theDesiredCardForDiscardingAll.getTrashPriority(owner)>theCardToBeBought.getTrashPriority(owner))) {
          theDesiredCard = theDesiredCardForDiscardingAll;
          j = theDesiredCardForDiscardingAll.getCoinCost(owner);
      }

      for (i=0;i<j;i++) {
          owner.discard(owner.getCardsInHand().remove(0));
      }
      owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(theDesiredCard));
    }

    private void handleHuman() {
        owner.setNeedsToUpdateGUI();
        if (!owner.getEngine().getGameFrame().askPlayer("<html>Use " + DomCardName.Artificer.toHTML() +"?</html>", "Resolving " + this.getName().toString()))
           return;
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("Choose cards to discard" , owner.getCardsInHand(), theChosenCards, 0);
        for (DomCard theCardName: theChosenCards) {
            owner.discardFromHand(owner.getCardsFromHand(theCardName.getName()).get(0));
        }
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (new DomCost(theChosenCards.size(),0).customCompare(theCard.getCost(owner.getCurrentGame()))==0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!")));
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }
}