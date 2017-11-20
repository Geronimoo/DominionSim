package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class The_Forest$s_GiftCard extends DomCard {

    public The_Forest$s_GiftCard() {
      super( DomCardName.The_Forest$s_Gift);
    }
    
    @Override
    public void play() {
    	owner.addAvailableCoins(1);
    	owner.addAvailableBuys(1);
    	owner.keepBoon(this);
    }
}