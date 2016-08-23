package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Ruined_VillageCard extends DomCard {

    public Ruined_VillageCard () {
      super( DomCardName.Ruined_Village);
    }
    
    @Override
    public void play() {
    	owner.addActions(1);
    }
}