package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Ruined_MarketCard extends DomCard {

    public Ruined_MarketCard () {
      super( DomCardName.Ruined_Market);
    }
    
    @Override
    public void play() {
    	owner.addAvailableBuys(1);
    }
}