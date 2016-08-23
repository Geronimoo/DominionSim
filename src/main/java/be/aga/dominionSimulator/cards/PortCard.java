package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class PortCard extends DomCard {
    public PortCard() {
      super( DomCardName.Port);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(1);
    }

    public void doWhenBought() {
        owner.gain(DomCardName.Port);
    }
}