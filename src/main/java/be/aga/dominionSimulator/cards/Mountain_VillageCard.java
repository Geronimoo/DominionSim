package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class Mountain_VillageCard extends DomCard {
    public Mountain_VillageCard() {
      super( DomCardName.Mountain_Village);
    }

    public void play() {
        owner.addActions(2);
        ArrayList<DomCard> theDiscard = owner.getCardsFromDiscard();
        if (theDiscard.isEmpty()) {
            if (DomEngine.haveToLog) DomEngine.addToLog("Discard is empty");
            owner.drawCards(1);
            return;
        }
        if (owner.isHumanOrPossessedByHuman()) {
            handleHumanPlayer(theDiscard);
        } else {
            Collections.sort(theDiscard, SORT_FOR_DISCARDING);
            owner.putInHand(owner.removeCardFromDiscard(theDiscard.get(theDiscard.size() - 1)));
        }
    }

    private void handleHumanPlayer(ArrayList<DomCard> theDiscard) {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : theDiscard)
           theChooseFrom.add(theCard.getName());
        DomCardName theChosenCard=theChooseFrom.get(0);
        if (theDiscard.size()>1)
          theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select card for " + this.getName().toString(), theChooseFrom, "Mandatory!");
        for (DomCard theCard:theDiscard) {
            if (theCard.getName()==theChosenCard) {
                owner.putInHand(owner.removeCardFromDiscard(theCard));
                break;
            }
        }
    }
}