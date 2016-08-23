package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class MonumentCard extends DomCard {
    public MonumentCard () {
      super( DomCardName.Monument);
    }

    public void play() {
      owner.addAvailableCoins( 2 );
      owner.addVP( 1);
    }
}