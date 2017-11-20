package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class The_Sky$s_GiftCard extends DomCard {
    public The_Sky$s_GiftCard() {
      super( DomCardName.The_Sky$s_Gift);
    }

    public void play() {
      if (owner.getCardsInHand().size()<3)
          return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      Collections.sort(owner.getCardsInHand(),SORT_FOR_DISCARD_FROM_HAND);
      int i=0;
      DomCard theNextCard = owner.getCardsInHand().get(i);
      while (theNextCard.getDiscardPriority(owner.getActionsLeft())<=DomCardName.Silver.getDiscardPriority(1) && i<owner.getCardsInHand().size()-1)
          theNextCard= owner.getCardsInHand().get(++i);
      if (i>=2) {
          for (i=0;i<3;i++) {
              owner.discard(owner.getCardsInHand().remove(0));
          }
          owner.gain(DomCardName.Gold);
      }
    }

    private void handleHuman() {
        owner.setNeedsToUpdate();
        if (!owner.getEngine().getGameFrame().askPlayer("<html>Discard 3 cards to gain " + DomCardName.Gold.toHTML() +"?</html>", "Resolving " + this.getName().toString()))
           return;
        ArrayList<DomCardName> theChosenCards = new ArrayList<DomCardName>();
        owner.getEngine().getGameFrame().askToSelectCards("Discard 3 cards" , owner.getCardsInHand(), theChosenCards, 3);
        for (DomCardName theCardName: theChosenCards) {
            owner.discardFromHand(owner.getCardsFromHand(theCardName).get(0));
        }
        owner.gain(DomCardName.Gold);
    }
}