package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class DungeonCard extends DomCard {
    public DungeonCard() {
      super( DomCardName.Dungeon);
    }

    public void play() {
      owner.addActions( 1 );
      owner.drawCards( 2 );
      owner.doForcedDiscard( 2, false );
    }

    @Override
    public void resolveDuration() {
        owner.drawCards( 2 );
        owner.doForcedDiscard( 2, false );
    }

    @Override
    public boolean wantsToBePlayed() {
    	if (owner.getDeckSize()<2)
    	  return false;
		return true;
    }
}