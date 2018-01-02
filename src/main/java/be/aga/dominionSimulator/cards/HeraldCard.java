package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class HeraldCard extends DomCard {
    public HeraldCard() {
        super(DomCardName.Herald);
    }

    public void play() {
        owner.addActions(1);
        owner.drawCards(1);
        if (owner.getDeckSize() == 0)
            return;
        DomCard theRevealedCard = owner.revealTopCards(1).get(0);
        if (theRevealedCard.hasCardType(DomCardType.Action))
            owner.play(theRevealedCard);
        else
            owner.putOnTopOfDeck(theRevealedCard);
    }

    public void doWhenBought() {
        if (owner.isHumanOrPossessedByHuman()) {
            doHumanWhenBought();
            return;
        }
        if (owner.getAvailableCoins() == 0)
            return;
        if (owner.getBuysLeft() > 0 && owner.getDesiredCard(owner.getTotalAvailableCurrency(), false) != null)
            return;
        Collections.sort(owner.getCardsFromDiscard(), SORT_FOR_DISCARDING);
        ArrayList<DomCard> theCardsToConsider = new ArrayList<DomCard>();
        for (int i = owner.getCardsFromDiscard().size() - 1; i >= 0; i--) {
            if (owner.getCardsFromDiscard().get(i).getDiscardPriority(1) >= DomCardName.Silver.getDiscardPriority(1))
                if (owner.getCardsFromDiscard().get(i) != this)
                    theCardsToConsider.add(owner.getCardsFromDiscard().get(i));
        }
        if (theCardsToConsider.isEmpty())
            return;
        int theTotalCards = Math.min(theCardsToConsider.size(), owner.getAvailableCoins());
        if (DomEngine.haveToLog) DomEngine.addToLog(owner + " overbuys for $" + theTotalCards);
        owner.spend(theTotalCards);
        for (int i = 0; i < theTotalCards; i++) {
            owner.putOnTopOfDeck(owner.removeCardFromDiscard(theCardsToConsider.get(i)));
        }
    }

    private void doHumanWhenBought() {
        if (owner.getAvailableCoinsWithoutTokens()==0)
            return;
        ArrayList<String> theOptions = new ArrayList<String>();
        for (int i = 1; i <= owner.getAvailableCoinsWithoutTokens(); i++) {
            theOptions.add("Overpay $" + i);
        }
        int theOverpayAmount = owner.getEngine().getGameFrame().askToSelectOption("Overpay?", theOptions, "Don't overpay");
        if (theOverpayAmount == -1)
            return;
        owner.addAvailableCoins(-(theOverpayAmount + 1));
        if (DomEngine.haveToLog)
            DomEngine.addToLog(owner + " overpays $" + (theOverpayAmount + 1));
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        int theSize = 0;
        if ((theOverpayAmount + 1) > owner.getCardsFromDiscard().size())
            theSize = owner.getCardsFromDiscard().size();
        else
            theSize = theOverpayAmount + 1;
        owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>", owner.getCardsFromDiscard(), theChosenCards, theSize);
        for (int i = theChosenCards.size() - 1; i >= 0; i--) {
            for (DomCard theCard : owner.getCardsFromDiscard()) {
                if (theChosenCards.get(i).getName() == theCard.getName()) {
                    owner.putOnTopOfDeck(theCard);
                    owner.removeCardFromDiscard(theCard);
                    break;
                }
            }
        }
    }
}