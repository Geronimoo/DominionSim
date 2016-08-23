package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class CacheCard extends DomCard {
    public CacheCard () {
      super( DomCardName.Cache);
    }

    @Override
    public void doWhenGained() {
    	for (int i=0;i<2;i++){
	        if (owner.getCurrentGame().countInSupply(DomCardName.Copper)>0)
	          owner.gain(DomCardName.Copper);
    	}
    }
}