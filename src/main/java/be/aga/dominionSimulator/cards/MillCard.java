package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class MillCard extends DomCard {
    public MillCard() {
      super( DomCardName.Mill);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      if (owner.getCardsInHand().size()<2)
          return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
      } else {
//          //Menagerie combo not working out great
//          if (owner.getCardsFromHand(DomCardName.Menagerie).isEmpty()) {
//              owner.doForcedDiscard(2,false);
//              owner.addAvailableCoins(2);
//          } else {
              Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
              if (owner.getCardsInHand().get(1).getDiscardPriority(owner.getActionsLeft()) <= DomCardName.Copper.getDiscardPriority(1)) {
                  owner.discard(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
                  owner.discard(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
                  owner.addAvailableCoins(2);
              }
//          }
      }
    }

    private void handleHuman() {
        owner.setNeedsToUpdate();
        if (owner.getEngine().getGameFrame().askPlayer("<html>Use " + DomCardName.Mill.toHTML() +"</html>", "Resolving Mill" )) {
            owner.doForcedDiscard(2, false);
            owner.addAvailableCoins(2);
        }
    }
}