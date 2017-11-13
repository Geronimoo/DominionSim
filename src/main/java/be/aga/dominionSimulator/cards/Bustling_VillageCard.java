package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Bustling_VillageCard extends DomCard {
    public Bustling_VillageCard() {
      super( DomCardName.Bustling_Village);
    }

    public void play() {
      owner.addActions(3);
      owner.drawCards(1);
      if (owner.isHumanOrPossessedByHuman()){
          handleHuman();
          return;
      }
      DomCard theSettler = owner.removeFromDiscard(DomCardName.Settlers);
      if (theSettler!=null)
        owner.putInHand(theSettler);
    }

    private void handleHuman() {
        owner.setNeedsToUpdate();
        for (DomCard theCard:owner.getCardsFromDiscard()){
            if (theCard.getName()==DomCardName.Settlers) {
                if (owner.getEngine().getGameFrame().askPlayer("<html>Take " + DomCardName.Settlers.toHTML() +"?</html>", "Resolving " + this.getName().toString())) {
                    DomCard theSettlers = owner.removeFromDiscard(DomCardName.Settlers);
                    owner.putInHand(theSettlers);
                }
                return;
            }
        }
    }
}