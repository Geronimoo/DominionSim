package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class QuarryCard extends DomCard {
    public QuarryCard () {
      super( DomCardName.Quarry);
    }
    
    @Override
    public void play() {
      owner.addAvailableCoins(1);
    }
}