package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Hunting_LodgeCard extends DomCard {
    public Hunting_LodgeCard() {
      super( DomCardName.Hunting_Lodge);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(1);
      int theTotal=0;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      for (DomCard card : owner.getCardsInHand()){
            theTotal+=card.getDiscardPriority(owner.getActionsAndVillagersLeft());
//            if (card.getName()==DomCardName.Tunnel){
//                owner.discardHand();
//                owner.drawCards(5);
//                return;
//            }
      }
      if (theTotal<80) {
        owner.discardHand();
        owner.drawCards(5);
      }
    }

    private void handleHuman() {
        owner.setNeedsToUpdateGUI();
        if (owner.getEngine().getGameFrame().askPlayer("<html>Discard Hand?</html>", "Resolving " + this.getName().toString())){
            owner.discardHand();
            owner.drawCards(5);
        }
    }
}