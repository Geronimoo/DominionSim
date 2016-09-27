package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class SentryCard extends DomCard {
    public SentryCard() {
      super( DomCardName.Sentry);
    }

    public void play() {
        owner.addActions(1);
        owner.drawCards(1);
        ArrayList<DomCard> theTopCards = owner.revealTopCards(2);
        Collections.sort(theTopCards, SORT_FOR_DISCARDING);
        if (theTopCards.isEmpty())
            return;
        while (!theTopCards.isEmpty()) {
            if (theTopCards.get(0).getTrashPriority() <= DomCardName.Copper.getTrashPriority()) {
                owner.trash(theTopCards.remove(0));
            } else {
                if (theTopCards.get(0).getDiscardPriority(1) <= DomCardName.Copper.getTrashPriority()) {
                    owner.discard(theTopCards.remove(0));
                } else {
                    owner.putOnTopOfDeck(theTopCards.remove(0));
                }

            }
        }
    }
}