package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class HighwayCard extends DomCard {

	public HighwayCard () {
      super( DomCardName.Highway);
    }

    public void play() {        
      owner.addActions(1);
      owner.drawCards(1);
   }
}