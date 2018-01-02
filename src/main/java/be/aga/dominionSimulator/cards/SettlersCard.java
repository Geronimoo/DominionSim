package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class SettlersCard extends DomCard {
    public SettlersCard() {
      super( DomCardName.Settlers);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
      } else {
          DomCard theCopper = owner.removeFromDiscard(DomCardName.Copper);
          if (theCopper != null)
              owner.putInHand(theCopper);
      }
    }

    private void handleHuman() {
        owner.setNeedsToUpdate();
        for (DomCard theCard:owner.getCardsFromDiscard()){
            if (theCard.getName()==DomCardName.Copper) {
                if (owner.getEngine().getGameFrame().askPlayer("<html>Take " + DomCardName.Copper.toHTML() +"?</html>", "Resolving " + this.getName().toString())) {
                    DomCard theCopper = owner.removeFromDiscard(DomCardName.Copper);
                    owner.putInHand(theCopper);
                }
                return;
            }
        }
    }
}