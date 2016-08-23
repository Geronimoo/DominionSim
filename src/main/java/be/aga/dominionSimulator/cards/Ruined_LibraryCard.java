package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Ruined_LibraryCard extends DomCard {

    public Ruined_LibraryCard () {
      super( DomCardName.Ruined_Library);
    }
    
    @Override
    public void play() {
    	owner.drawCards(1);
    }
}