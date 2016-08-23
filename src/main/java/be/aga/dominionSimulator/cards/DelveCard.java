package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class DelveCard extends DomCard {
    public DelveCard() {
      super( DomCardName.Delve);
    }

    public void play() {
      owner.addAvailableBuys(1);
      owner.gain(DomCardName.Silver);
    }
}