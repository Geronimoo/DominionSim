package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class MarauderCard extends DomCard {
    public MarauderCard () {
      super( DomCardName.Marauder);
    }

    public void play() {
	    for (DomPlayer thePlayer : owner.getOpponents()) {
	      if (!thePlayer.checkDefense()) {
	    	  if (owner.getCurrentGame().countInSupply(DomCardName.Ruins)>0)
	    	    thePlayer.gain(DomCardName.Ruins);
	      }
	    }
        owner.gain(DomCardName.Spoils);
    }
}