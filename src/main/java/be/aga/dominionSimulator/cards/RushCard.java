package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class RushCard extends DomCard {
    public RushCard() {
      super( DomCardName.Rush);
    }

    public void play() {
        owner.addAvailableBuys(1);
        owner.triggerRush();
    }
}