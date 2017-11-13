package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class AlchemistCard extends DomCard {
    public AlchemistCard () {
      super( DomCardName.Alchemist);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(2);
    }

    @Override
    public void handleCleanUpPhase() {
      for (DomCard theCard : owner.getCardsInPlay()) {
        if (theCard.getName()==DomCardName.Potion) {
          if (owner.isHumanOrPossessedByHuman()) {
              if (owner.getEngine().getGameFrame().askPlayer("<html>Put back " + DomCardName.Alchemist.toHTML() +" ?</html>", "Resolving " + this.getName().toString())) {
                  owner.putOnTopOfDeck(this);
                  return;
              }
          } else {
              owner.putOnTopOfDeck(this);
              return;
          }
        }
      }
      super.handleCleanUpPhase();
    }
}