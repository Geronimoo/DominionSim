package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class WarehouseCard extends DomCard {
    public WarehouseCard () {
      super( DomCardName.Warehouse);
    }

    public void play() {
      owner.addActions( 1 );
      owner.drawCards( 3 );
      owner.doForcedDiscard( 3, false );
    }
    
    @Override
    public boolean wantsToBePlayed() {
    	if (owner.getDeckSize()<2)
    	  return false;
		return true;
    }
}