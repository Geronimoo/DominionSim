package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;

import java.util.ArrayList;
import java.util.Collections;

public class CapitalCard extends DomCard {
    public CapitalCard() {
      super( DomCardName.Capital);
    }

    public void play() {
        owner.addAvailableBuys(1);
        owner.addAvailableCoins(6);
    }

    @Override
    public void doWhenDiscarded() {
        if (owner.getPhase()== DomPhase.CleanUp) {
            owner.addDebt(6);
            owner.payOffDebt();
        }
    }
}