package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class HoardCard extends DomCard {

    public HoardCard () {
      super( DomCardName.Hoard);
    }
    
    @Override
    public void play() {
      owner.addAvailableCoins(2);
      owner.increaseHoardCount();
    }
}