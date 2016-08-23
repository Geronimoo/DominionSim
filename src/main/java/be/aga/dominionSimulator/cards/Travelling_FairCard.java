package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class Travelling_FairCard extends DomCard {
    public Travelling_FairCard() {
      super( DomCardName.Travelling_Fair);
    }

    public void play() {
      owner.addAvailableBuys(2);
      owner.activateTravellingFair();
    }
}