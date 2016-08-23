package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class PrincessCard extends DomCard {

	public PrincessCard () {
      super( DomCardName.Princess);
    }

    public void play() {        
      owner.addAvailableBuys(1);
   }
}