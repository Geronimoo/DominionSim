package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class ForumCard extends DomCard {
    public ForumCard() {
      super( DomCardName.Forum);
    }

    public void play() {
      owner.addActions( 1 );
      owner.drawCards( 3 );
      owner.doForcedDiscard( 2, false );
    }
    
    @Override
    public boolean wantsToBePlayed() {
    	if (owner.getDeckSize()<2)
    	  return false;
		return true;
    }

    public void doWhenBought() {
        owner.addAvailableBuys(1);
    }
}