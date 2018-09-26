package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class
        SaunaCard extends DomCard {
    public SaunaCard() {
      super( DomCardName.Sauna);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      if (!owner.getCardsFromHand(DomCardName.Avanto).isEmpty()) {
        if (owner.isHumanOrPossessedByHuman()) {
            owner.setNeedsToUpdateGUI();
            if (owner.getEngine().getGameFrame().askPlayer("<html>Chain " + DomCardName.Avanto.toHTML() +"?</html>", "Resolving " + this.getName().toString()))
                owner.play(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Avanto).get(0)));
        } else {
            owner.play(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Avanto).get(0)));
        }
      }
    }
}