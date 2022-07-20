package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class HunterCard extends DomCard {
    public HunterCard() {
        super(DomCardName.Hunter);
    }

    public void play() {
        owner.addActions(1);
        ArrayList<DomCard> theRevealedCards = owner.revealTopCards(3);
        if (theRevealedCards.isEmpty())
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman(theRevealedCards);
            return;
        }
        theRevealedCards.sort(SORT_FOR_DISCARDING_REVERSE);
        boolean victoryChosen = false;
        boolean actionChosen = false;
        boolean treasureChosen = false;
        ArrayList<DomCard> theCardsToDiscard = new ArrayList<>();
        while (!theRevealedCards.isEmpty()) {
            if (theRevealedCards.get(0).hasCardType(DomCardType.Victory) && !victoryChosen) {
                owner.addCardToHand(theRevealedCards.remove(0));
                victoryChosen = true;
            }
            if (!theRevealedCards.isEmpty() && theRevealedCards.get(0).hasCardType(DomCardType.Treasure) && !treasureChosen) {
                owner.addCardToHand(theRevealedCards.remove(0));
                treasureChosen = true;
            }
            if (!theRevealedCards.isEmpty() && theRevealedCards.get(0).hasCardType(DomCardType.Action) && !actionChosen) {
                owner.addCardToHand(theRevealedCards.remove(0));
                actionChosen = true;
            }
            if (!theRevealedCards.isEmpty())
              theCardsToDiscard.add(theRevealedCards.remove(0));
        }
        if (!theCardsToDiscard.isEmpty()) {
            owner.discard(theCardsToDiscard);
        }
    }

    private void handleHuman(ArrayList<DomCard> theRevealedCards) {
        //TODO implement this
//        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
//        for (DomCard theCard : theRevealedCards) {
//            if (theCard.hasCardType(DomCardType.Victory) || theCard.hasCardType(DomCardType.Treasure) || theCard.hasCardType(DomCardType.Action))
//              theChooseFrom.add(theCard.getName());
//        }
//        if (theChooseFrom.isEmpty()) {
//            owner.discard(theRevealedCards);
//            return;
//        }
//        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
//        owner.getEngine().getGameFrame().askToSelectCards("<html>Choose your cards</html>", theRevealedCards, theChosenCards, 0);
//        for (DomCard theChosenCard : theChosenCards) {
//
//        }
//        DomCardType theCardType = null;
//        for (DomCard theCard : theRevealedCards) {
//            if (theCard.getName()==theChosenCard) {
//                theRevealedCards.remove(theCard);
//                owner.addCardToHand(theCard);
//                break;
//            }
//        }
//        if (theRevealedCards.isEmpty())
//            return;
//        theChooseFrom=new ArrayList<DomCardName>();
//        for (DomCard theCard : theRevealedCards) {
//            theChooseFrom.add(theCard.getName());
//        }
//        theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Discard a card", theChooseFrom, "Mandatory!");
//        for (DomCard theCard : theRevealedCards) {
//            if (theCard.getName()==theChosenCard) {
//                theRevealedCards.remove(theCard);
//                owner.discard(theCard);
//                break;
//            }
//        }
//        if (theRevealedCards.isEmpty())
//            return;
//        owner.putOnTopOfDeck(theRevealedCards.get(0));
    }
}

