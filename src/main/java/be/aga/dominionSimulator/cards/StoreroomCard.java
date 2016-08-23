package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class StoreroomCard extends DomCard {
    public StoreroomCard() {
      super( DomCardName.Storeroom);
    }

    public void play() {
      owner.addAvailableBuys(1);
      Collections.sort(owner.getCardsInHand(),SORT_FOR_DISCARD_FROM_HAND);
      int i=0;
      for (i=0;i<owner.getCardsInHand().size();i++) {
          if (owner.getCardsInHand().get(i).getDiscardPriority(owner.getActionsLeft())>DomCardName.Copper.getDiscardPriority(1))
              break;
      }
      for (int j=0;j<i;j++)
          owner.discardFromHand(owner.getCardsInHand().get(0));
      if (i>0)
        owner.drawCards(i);

      Collections.sort(owner.getCardsInHand(),SORT_FOR_DISCARD_FROM_HAND);
      for (i=0;i<owner.getCardsInHand().size();i++) {
          if (owner.getCardsInHand().get(i).getDiscardPriority(owner.getActionsLeft())>DomCardName.Copper.getDiscardPriority(1))
              break;
      }
      for (int j=0;j<i;j++)
          owner.discardFromHand(owner.getCardsInHand().get(0));
      if (i>0)
        owner.addAvailableCoins(i);
    }
}