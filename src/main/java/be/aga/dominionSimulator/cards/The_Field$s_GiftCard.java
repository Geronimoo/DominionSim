package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class The_Field$s_GiftCard extends DomCard {

    public The_Field$s_GiftCard() {
      super( DomCardName.The_Field$s_Gift);
    }
    
    @Override
    public void play() {
        owner.addActions(1);
        owner.addAvailableCoins(1);
    	owner.keepBoon(this);
    }
}