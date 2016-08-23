package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.Collections;

public class BonfireCard extends DomCard {

    public BonfireCard() {
        super(DomCardName.Bonfire);
    }

    public void play() {
        if (owner.getCardsInPlay().isEmpty())
            return;
        Collections.sort(owner.getCardsInPlay(), SORT_FOR_TRASHING);
        int i = countCrapCards();
        int count = 0;
        while (i > 0 && count < 2) {
            owner.trash(owner.removeCardFromPlay(owner.getCardsInPlay().get(0)));
            count++;
            i--;
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