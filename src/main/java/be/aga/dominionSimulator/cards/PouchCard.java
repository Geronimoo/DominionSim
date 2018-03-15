package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class PouchCard extends DomCard {
    public PouchCard() {
      super( DomCardName.Pouch);
    }

    public void play() {
        owner.addAvailableBuys(1);
        owner.addAvailableCoins(1);
    }
}