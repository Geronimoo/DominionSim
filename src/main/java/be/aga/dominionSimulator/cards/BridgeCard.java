package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class BridgeCard extends DomCard {

	public BridgeCard () {
      super( DomCardName.Bridge);
    }

    public void play() {        
      owner.addAvailableBuys(1);
      owner.addAvailableCoins(1);
      owner.increaseBridgePlayedCounter();
   }
}