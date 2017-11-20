package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class The_Sea$s_GiftCard extends DomCard {

	public The_Sea$s_GiftCard() {
      super( DomCardName.The_Sea$s_Gift);
    }

    public void play() {
        owner.drawCards(1);
    }
}