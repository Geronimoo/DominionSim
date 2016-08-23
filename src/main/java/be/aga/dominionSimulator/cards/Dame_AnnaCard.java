package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class Dame_AnnaCard extends KnightCard {

    public Dame_AnnaCard() {
        super(DomCardName.Dame_Anna);
    }

    public void play() {
        if (!owner.getCardsInHand().isEmpty()) {
            Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
            int i = countCrapCards();
            int count = 0;
            while (i > 0 && count < 2) {
                owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
                count++;
                i--;
            }
        }
        super.play();
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