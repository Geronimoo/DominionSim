package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class FarrierCard extends DomCard {
    public FarrierCard() {
        super(DomCardName.Farrier);
    }

    public void play() {
        owner.addActions(1);
        owner.addAvailableBuys(1);
        owner.drawCards(1);
    }

    public void doWhenBought() {
        if (owner.isHumanOrPossessedByHuman()) {
            doHumanWhenBought();
            return;
        }
        if (owner.getAvailableCoins() == 0)
            return;
//        if (owner.getBuysLeft() > 1
//                && owner.getDesiredCard(owner.getTotalAvailableCurrency(), false) != null
//                && owner.getDesiredCard(owner.getTotalAvailableCurrency(), false) != DomCardName.Farrier
//                && owner.getDesiredCard(owner.getTotalAvailableCurrency(), false) != DomCardName.Copper)
//            return;
//
        int theTotalCards = owner.getAvailableCoins();
        if (DomEngine.haveToLog) DomEngine.addToLog(owner + " overbuys for $" + theTotalCards);
        owner.spend(theTotalCards);
        owner.addEndTurnExtraDraws(theTotalCards);
    }

    private void doHumanWhenBought() {
        if (owner.getAvailableCoins()==0)
            return;
        ArrayList<String> theOptions = new ArrayList<String>();
        for (int i = 1; i <= owner.getAvailableCoins(); i++) {
            theOptions.add("Overpay $" + i);
        }
        int theOverpayAmount = owner.getEngine().getGameFrame().askToSelectOption("Overpay?", theOptions, "Don't overpay");
        if (theOverpayAmount == -1)
            return;
        if (DomEngine.haveToLog)
            DomEngine.addToLog(owner + " overpays $" + (theOverpayAmount + 1));
        owner.spend(theOverpayAmount + 1);
        owner.addEndTurnExtraDraws(theOverpayAmount+1);
    }
}