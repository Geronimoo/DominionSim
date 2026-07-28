package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class Tea_HouseCard extends DomCard {
    public Tea_HouseCard() {
      super( DomCardName.Tea_House);
    }

    public void play() {
        owner.addSunForProphecy(1);
        owner.addActions(1);
        owner.drawCards(1);
        owner.addAvailableCoins(2);
    }
}