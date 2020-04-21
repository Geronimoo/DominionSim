package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class StampedeCard extends DomCard {
    public StampedeCard() {
      super( DomCardName.Stampede);
    }

    public void play() {
        if (owner.getCardsInPlay().size()>5) {
            if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has more than 5 cards in play so Stampede has no effect");
        } else {
            for (int i=0;i<5;i++) {
                DomCard theHorse = owner.getCurrentGame().takeFromSupply(DomCardName.Horse);
                if (theHorse!=null)
                    owner.gainOnTopOfDeck(theHorse);
            }
        }
    }
}