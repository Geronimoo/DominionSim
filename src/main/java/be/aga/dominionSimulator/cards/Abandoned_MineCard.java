package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Abandoned_MineCard extends DomCard {

    public Abandoned_MineCard () {
      super( DomCardName.Abandoned_Mine);
    }
    
    @Override
    public void play() {
      owner.addAvailableCoins(1);
    }
}