package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Root_CellarCard extends DomCard {
    public Root_CellarCard() {
      super( DomCardName.Root_Cellar);
    }

    public void play() {
      owner.addActions( 1 );
      owner.drawCards( 3 );
      owner.addDebt(3);
    }
    
    @Override
    public boolean wantsToBePlayed() {
    	if (owner.getDeckAndDiscardSize()<2)
    	  return false;
		return true;
    }
}