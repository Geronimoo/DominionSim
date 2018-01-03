package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class Dame_AnnaCard extends KnightCard {

    public Dame_AnnaCard() {
        super(DomCardName.Dame_Anna);
    }

    public void play() {
        if (!owner.getCardsInHand().isEmpty()) {
            if (owner.isHumanOrPossessedByHuman()) {
                handleHuman();
            } else {
                Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
                int i = countCrapCards();
                int count = 0;
                while (i > 0 && count < 2) {
                    owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
                    count++;
                    i--;
                }
            }
        }
        super.play();
    }

    private void handleHuman() {
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        do {
            theChosenCards = new ArrayList<DomCard>();
            owner.getEngine().getGameFrame().askToSelectCards("Trash (max 2)", owner.getCardsInHand(), theChosenCards, 0);
        } while (theChosenCards.size()>2);
        while (!theChosenCards.isEmpty()) {
            DomCard theCardToTrash = null;
            for (DomCard theCard : owner.getCardsInHand()) {
                if (theCard.getName() == theChosenCards.get(0).getName())
                    theCardToTrash = theCard;
            }
            theChosenCards.remove(0);
            owner.trash(owner.removeCardFromHand(theCardToTrash));
        }
    }

    private int countCrapCards() {
        int counter = 0;
        for (DomCard theCard : owner.getCardsInHand()) {
            if (theCard.getTrashPriority() <= DomCardName.Copper.getTrashPriority())
                counter++;
        }
        return counter;
    }
}