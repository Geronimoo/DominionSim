package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class TalismanCard extends DomCard {

    public TalismanCard () {
      super( DomCardName.Talisman);
    }
    
    @Override
    public void play() {
      owner.addAvailableCoins(1);
    }
}