package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class UnderlingCard extends DomCard {
    public UnderlingCard() {
      super( DomCardName.Underling);
    }

    public void play() {
      owner.addActions(1);
      owner.addFavors(1);
      owner.drawCards(1);
    }
}