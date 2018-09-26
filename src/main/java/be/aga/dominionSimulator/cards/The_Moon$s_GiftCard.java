package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class The_Moon$s_GiftCard extends DomCard {
    public The_Moon$s_GiftCard() {
      super( DomCardName.The_Moon$s_Gift);
    }

    public void play() {
        ArrayList<DomCard> theDiscard = owner.getCardsFromDiscard();
        if (theDiscard.isEmpty()) {
            if (DomEngine.haveToLog) DomEngine.addToLog("Discard is empty");
            return;
        }
        if (owner.isHumanOrPossessedByHuman()) {
            handleHumanPlayer(theDiscard);
        } else {
            Collections.sort(theDiscard, SORT_FOR_DISCARDING);
            if (theDiscard.get(theDiscard.size() - 1).getDiscardPriority(1) > DomCardName.Copper.getDiscardPriority(1))
                owner.putOnTopOfDeck(owner.removeCardFromDiscard(theDiscard.get(theDiscard.size() - 1)));
            else
                if (DomEngine.haveToLog)
                  DomEngine.addToLog(owner + " has no good card in discard to put on top of deck");
        }
    }

    private void handleHumanPlayer(ArrayList<DomCard> theDiscard) {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : theDiscard)
           theChooseFrom.add(theCard.getName());
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select card for " + this.getName().toString(), theChooseFrom, "Don't use");
        for (DomCard theCard:theDiscard) {
            if (theCard.getName()==theChosenCard) {
                owner.putOnTopOfDeck(owner.removeCardFromDiscard(theCard));
                break;
            }
        }
    }
}