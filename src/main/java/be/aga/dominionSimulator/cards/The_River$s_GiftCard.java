package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class The_River$s_GiftCard extends DomCard {

	public The_River$s_GiftCard() {
      super( DomCardName.The_River$s_Gift);
    }

    public void play() {
        owner.activateRiver$sGift();
        owner.keepBoon(this);
    }
}