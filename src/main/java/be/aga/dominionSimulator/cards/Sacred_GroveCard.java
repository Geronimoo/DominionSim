package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Sacred_GroveCard extends DomCard {
    public Sacred_GroveCard() {
      super( DomCardName.Sacred_Grove);
    }

    public void play() {
      owner.addAvailableBuys(1);
      owner.addAvailableCoins(3);
      DomCard theBoon = owner.takeBoon();
      owner.receiveBoon(theBoon);
      if (theBoon.getName()!=DomCardName.The_Forest$s_Gift && theBoon.getName()!=DomCardName.The_Field$s_Gift) {
          for (DomPlayer theOpp : owner.getOpponents()) {
              theOpp.receiveBoon(theBoon);
          }
      }
    }
}