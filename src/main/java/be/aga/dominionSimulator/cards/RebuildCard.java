package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class RebuildCard extends DomCard {
    public RebuildCard() {
      super( DomCardName.Rebuild);
    }

    public void play() {
        owner.addActions(1);
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        DomCardName theNamedCard;
        if (owner.countInDeck(DomCardName.Estate) - owner.getCardsFromHand(DomCardName.Estate).size() > 0
                && owner.getCurrentGame().countInSupply(DomCardName.Duchy) > 0
                && owner.countInDeck(DomCardName.Province)-owner.getCardsFromHand(DomCardName.Province).size()==0) {
            theNamedCard = DomCardName.Duchy;
        } else {
            theNamedCard = DomCardName.Province;
        }
        if (owner.countInDeck(DomCardName.Duchy)==0 && owner.getCurrentGame().countInSupply(DomCardName.Duchy)==0 && owner.getCurrentGame().countInSupply(DomCardName.Province)>0)
            theNamedCard=DomCardName.Estate;

        if (owner.getCurrentGame().countInSupply(DomCardName.Colony)>0)
            theNamedCard=DomCardName.Colony;

        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " names " + theNamedCard);

        ArrayList<DomCard> theRevealedCards = owner.revealUntilVictoryCardNotNamed(theNamedCard);
        if (theRevealedCards.isEmpty())
            return;
        DomCard theLastCard = theRevealedCards.get(theRevealedCards.size() - 1);
        if (theLastCard.hasCardType(DomCardType.Victory) && theLastCard.getName() != theNamedCard) {
            DomCost theCardCost = theLastCard.getCost(owner.getCurrentGame());
            owner.trash(theRevealedCards.remove(theRevealedCards.size() - 1));
            DomCardName theDesiredCard = owner.getDesiredCard(DomCardType.Victory, theCardCost.add(new DomCost(3, 0)), false, false, null);
            if (theDesiredCard == null) {
                theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, DomCardType.Victory, theCardCost.add(new DomCost(3, 0)));
            }
            if (theDesiredCard != null) {
                owner.gain(theDesiredCard);
            } else {
                if (DomEngine.haveToLog) DomEngine.addToLog( owner + " gains nothing");
            }
        }
        owner.discard(theRevealedCards);
    }

    private void handleHuman() {
        ArrayList<DomCardName> theDeckCards = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getDeck().keySet()) {
            theDeckCards.add(theCard);
        }
        Collections.sort(theDeckCards);
        DomCardName theChoice = owner.getEngine().getGameFrame().askToSelectOneCard("Name a card", theDeckCards, "Name Ace of Spades");
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " names " + theChoice);

        ArrayList<DomCard> theRevealedCards = owner.revealUntilVictoryCardNotNamed(theChoice);
        if (theRevealedCards.isEmpty())
            return;
        DomCard theLastCard = theRevealedCards.get(theRevealedCards.size() - 1);
        if (theLastCard.hasCardType(DomCardType.Victory) && theLastCard.getName() != theChoice) {
            DomCost theCardCost = theLastCard.getCost(owner.getCurrentGame());
            owner.trash(theRevealedCards.remove(theRevealedCards.size() - 1));
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
                if (theCard.hasCardType(DomCardType.Victory) && theCardCost.add(new DomCost(3, 0)).compareTo(theCard.getCost(owner.getCurrentGame())) >= 0 && owner.getCurrentGame().countInSupply(theCard) > 0)
                    theChooseFrom.add(theCard);
            }
            if (theChooseFrom.isEmpty())
                return;
            if (theChooseFrom.size() == 1) {
                owner.gain(owner.getCurrentGame().takeFromSupply(theChooseFrom.get(0)));
            } else {
                owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Gain a card for " + this.getName().toString(), theChooseFrom, "Mandatory!")));
            }
        }
        owner.discard(theRevealedCards);
    }
}