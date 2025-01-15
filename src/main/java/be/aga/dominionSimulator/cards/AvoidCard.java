package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class AvoidCard extends DomCard {
    public AvoidCard() {
      super( DomCardName.Avoid);
    }

    public void play() {
        owner.addAvoidTrigger();
        owner.addAvailableBuys(1);
    }
}