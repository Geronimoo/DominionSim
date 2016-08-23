package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class MountebankCard extends DomCard {
    public MountebankCard () {
      super( DomCardName.Mountebank);
    }

    public void play() {
        owner.addAvailableCoins( 2 ); 
        for (DomPlayer thePlayer : owner.getOpponents()) {
          if (thePlayer.checkDefense() || discardsCurse(thePlayer)) 
        	continue;
          if (owner.getCurrentGame().countInSupply(DomCardName.Curse )>0)
            thePlayer.gain(DomCardName.Curse);
          if (owner.getCurrentGame().countInSupply(DomCardName.Copper )>0)
            thePlayer.gain(DomCardName.Copper);
        }
    }

	private boolean discardsCurse(DomPlayer thePlayer) {
		if (thePlayer.getCardsFromHand(DomCardName.Curse).isEmpty())
	       return false;
		if (thePlayer.getCurrentGame().countInSupply(DomCardName.Curse)==0
		  && !thePlayer.getCardsFromHand(DomCardType.Trasher).isEmpty())
			return false;
		thePlayer.discardFromHand(DomCardName.Curse);		
		return true;
	}
}