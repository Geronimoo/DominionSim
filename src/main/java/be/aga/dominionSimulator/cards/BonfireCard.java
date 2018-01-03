package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import java.util.ArrayList;
import java.util.Collections;

public class BonfireCard extends DomCard {

    public BonfireCard() {
        super(DomCardName.Bonfire);
    }

    public void play() {
        if (owner.getCardsInPlay().isEmpty())
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        Collections.sort(owner.getCardsInPlay(), SORT_FOR_TRASHING);
        int i = countCrapCards();
        int count = 0;
        while (i > 0 && count < 2) {
            owner.trash(owner.removeCardFromPlay(owner.getCardsInPlay().get(0)));
            count++;
            i--;
        }
    }

    private void handleHuman() {
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        do {
            theChosenCards = new ArrayList<DomCard>();
            owner.getEngine().getGameFrame().askToSelectCards("Trash", owner.getCardsInPlay(), theChosenCards, 0);
        } while (theChosenCards.size()>2);
        for (int i = theChosenCards.size() - 1; i >= 0; i--) {
            for (DomCard theCard : owner.getCardsInPlay()) {
                if (theChosenCards.get(i).getName() == theCard.getName()) {
                    owner.trash(owner.removeCardFromPlay(theCard));
                    break;
                }
            }
        }
    }

    private int countCrapCards() {
        int counter = 0;
        for (DomCard theCard : owner.getCardsInPlay()) {
            if (theCard.getTrashPriority() <= DomCardName.Copper.getTrashPriority())
                counter++;
        }
        return counter;
    }
}