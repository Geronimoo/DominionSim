package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class Rustic_VillageCard extends DomCard {
    public Rustic_VillageCard() {
      super( DomCardName.Rustic_Village);
    }

    public void play() {
        owner.addSunForProphecy(1);
        owner.addActions(2);
        owner.drawCards(1);
        Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARDING);
        int theDiscardCount=0;
        for (DomCard theCard : owner.getCardsInHand()) {
            theDiscardCount+= theCard.getDiscardPriority(owner.actionsLeft) < 15 ? 1 : 0;
        }
        if (theDiscardCount>=2) {
            owner.discardFromHand(owner.getCardsInHand().get(0));
            owner.discardFromHand(owner.getCardsInHand().get(0));
            owner.drawCards( 1 );
        } else {
            if (DomEngine.haveToLog) DomEngine.addToLog( owner + " discards nothing!" );
        }

    }
}