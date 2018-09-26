package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class AvantoCard extends DomCard {
    public AvantoCard() {
      super( DomCardName.Avanto);
    }

    public void play() {
      owner.drawCards(3);
      if (!owner.getCardsFromHand(DomCardName.Sauna).isEmpty()) {
          if (owner.isHumanOrPossessedByHuman()) {
              owner.setNeedsToUpdateGUI();
              if (owner.getEngine().getGameFrame().askPlayer("<html>Chain " + DomCardName.Sauna.toHTML() +"?</html>", "Resolving " + this.getName().toString()))
                  owner.play(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Sauna).get(0)));
          } else {
              owner.play(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Sauna).get(0)));
          }
      }
    }
}