package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class RitualCard extends DomCard {
    public RitualCard() {
      super( DomCardName.Ritual);
    }

    public void play() {
        DomCard theCurse = owner.getCurrentGame().takeFromSupply(DomCardName.Curse);
        if (theCurse == null) {
            if (DomEngine.haveToLog) DomEngine.addToLog("No effect because Curses are empty");
            return;
        }
        owner.gain(theCurse);
        if (owner.getCardsInHand().isEmpty())
            return;
        if (owner.isHumanOrPossessedByHuman()){
            handleHuman();
            return;
        }
        Collections.sort(owner.getCardsInHand(), SORT_BY_COIN_COST);
        for (int i = owner.getCardsInHand().size() - 1; i >= 0; i--) {
            DomCard theCard = owner.getCardsInHand().get(i);
            if (!theCard.hasCardType(DomCardType.Victory)) {
                owner.trash(owner.removeCardFromHand(theCard));
                owner.addVP(theCard.getCoinCost(owner.getCurrentGame()));
                return;
            }
        }
        DomCard theCard = owner.getCardsInHand().get(owner.getCardsInHand().size()-1);
        owner.trash(owner.removeCardFromHand(theCard));
        owner.addVP(theCard.getCoinCost(owner.getCurrentGame()));
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
        if (theChosenCard != null) {
            owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
            owner.addVP(theChosenCard.getCoinCost(owner));
        }
    }
}