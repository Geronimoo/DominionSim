package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

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
      Collections.sort(owner.getCardsInHand(),SORT_FOR_DISCARD_FROM_HAND);
      int i=0;
      DomCard theNextCard = owner.getCardsInHand().get(i);
      while (theNextCard.getDiscardPriority(owner.getActionsLeft())<=DomCardName.Copper.getDiscardPriority(1) && i<owner.getCardsInHand().size()-1)
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
          j = theDesiredCardForDiscardingAll.getCoinCost(owner.getCurrentGame());
      }

      for (i=0;i<j;i++) {
          owner.discard(owner.getCardsInHand().remove(0));
      }
      owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(theDesiredCard));
    }
}