package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class PlunderCard extends DomCard {
    public PlunderCard() {
      super( DomCardName.Plunder);
    }

    public void play() {
      owner.addAvailableCoins( 2 );
      owner.addVP( 1);
    }
}