package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class PeasantCard extends TravellerCard {
    public PeasantCard() {
      super( DomCardName.Peasant);
      myUpgrade=DomCardName.Soldier;
    }

    public void play() {
      owner.addAvailableCoins(1);
      owner.addAvailableBuys(1);
    }
}