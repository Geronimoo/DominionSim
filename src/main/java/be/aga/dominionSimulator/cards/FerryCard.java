package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class FerryCard extends DomCard {

	public FerryCard() {
      super( DomCardName.Ferry);
    }

    public void play() {
        owner.placeMinus$2Token();
    }
}