package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class The_Mountain$s_GiftCard extends DomCard {

    public The_Mountain$s_GiftCard() {
      super( DomCardName.The_Mountain$s_Gift);
    }
    
    @Override
    public void play() {
    	owner.gain(DomCardName.Silver);
    }
}