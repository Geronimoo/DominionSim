package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class InheritanceCard extends DomCard {

	public InheritanceCard() {
      super( DomCardName.Inheritance);
    }

    public void play() {
        owner.placeEstateToken();
    }
}