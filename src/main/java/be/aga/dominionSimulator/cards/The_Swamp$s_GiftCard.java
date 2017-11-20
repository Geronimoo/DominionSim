package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class The_Swamp$s_GiftCard extends DomCard {

	public The_Swamp$s_GiftCard() {
      super( DomCardName.The_Swamp$s_Gift);
    }

    public void play() {
        owner.gain(DomCardName.Will_o$_Wisp);
    }
}