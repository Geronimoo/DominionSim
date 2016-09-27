package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class HarbingerCard extends DomCard {
    public HarbingerCard() {
      super( DomCardName.Harbinger);
    }

    public void play() {
        owner.addActions(1);
        owner.drawCards(1);
        ArrayList<DomCard> theDiscard = owner.getCardsFromDiscard();
        if (theDiscard.isEmpty()) {
            if (DomEngine.haveToLog) DomEngine.addToLog( "Discard is empty" );
            return;
        }

        Collections.sort(theDiscard, SORT_FOR_DISCARDING);
        if (theDiscard.get(theDiscard.size()-1).getDiscardPriority(1)>DomCardName.Copper.getDiscardPriority(1))
          owner.putOnTopOfDeck(owner.removeCardFromDiscard(theDiscard.get(theDiscard.size()-1)));
        else
          if (DomEngine.haveToLog) DomEngine.addToLog( owner + " has no good card in discard to put on top of deck" );
    }
}