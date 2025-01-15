package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class BuryCard extends DomCard {
    public BuryCard() {
      super( DomCardName.Bury);
    }

    public void play() {
        owner.addAvailableBuys(1);
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        ArrayList<DomCard> cardsFromDiscard = owner.getCardsFromDiscard();
        Collections.sort(cardsFromDiscard, SORT_FOR_DISCARDING_REVERSE);
        owner.putOnBottomOfDeck(cardsFromDiscard.remove(0));
    }

    private void handleHuman() {
        //TODO
        owner.setNeedsToUpdateGUI();
        Set<DomCardName> uniqueCards = owner.getUniqueCardNamesInHand();
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        theChooseFrom.clear();
        theChooseFrom.addAll(uniqueCards);
        DomCard theChosenCard = owner.getCardsFromHand(owner.getEngine().getGameFrame().askToSelectOneCard("Put back a card for " + this.getName().toString(), theChooseFrom, "Mandatory!")).get(0);
        if (owner.getDrawDeckSize()==0) {
            owner.putOnTopOfDeck(owner.removeCardFromHand(theChosenCard));
        } else {
            ArrayList<String> theOptions = new ArrayList<String>();
            for (int i = 0; i < owner.getDrawDeckSize()+1; i++) {
                theOptions.add(i == 0 ? "Top" : i == owner.getDrawDeckSize()? "Bottom" : "Here");
            }
            int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Position?", theOptions, "Mandatory!");
            owner.putInDeckAt(owner.removeCardFromHand(theChosenCard), theChoice);
        }
    }
}