package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class CityCard extends DomCard {
    public CityCard () {
      super( DomCardName.City);
    }

    public void play() {
      owner.addActions(2);
      switch (owner.getCurrentGame().countEmptyPiles()) {
        case 0:
          owner.drawCards( 1 );
          break;
        case 1:
          owner.drawCards( 2 );
          break;
        default:
          owner.addAvailableCoins( 1 );
          owner.addAvailableBuys( 1 );
          owner.drawCards( 2 );
          break;
      }
    }
}