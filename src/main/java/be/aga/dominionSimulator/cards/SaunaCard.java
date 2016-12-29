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
        owner.play(owner.removeCardFromHand( owner.getCardsFromHand(DomCardName.Avanto).get(0)));
      }
    }
}