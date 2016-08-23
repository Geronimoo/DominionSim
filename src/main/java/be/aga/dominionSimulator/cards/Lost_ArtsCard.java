package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Lost_ArtsCard extends DomCard {

	public Lost_ArtsCard() {
      super( DomCardName.Lost_Arts);
    }

    public void play() {
        owner.placePlusOneActionToken();
    }
}