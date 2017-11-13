package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class TradeCard extends DomCard {
    public TradeCard() {
      super( DomCardName.Trade);
    }

    public void play() {
        if (owner.getCardsInHand().isEmpty())
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        DomCard theCardToTrash = owner.getCardsInHand().get(0);
        if (theCardToTrash.getTrashPriority()<=DomCardName.Copper.getTrashPriority()) {
            owner.trash(owner.removeCardFromHand(theCardToTrash));
            owner.gain(DomCardName.Silver);
            if (owner.getCardsInHand().isEmpty())
                return;
            theCardToTrash = owner.getCardsInHand().get(0);
            if (theCardToTrash.getTrashPriority() <= DomCardName.Copper.getTrashPriority()) {
                owner.trash(owner.removeCardFromHand(theCardToTrash));
                owner.gain(DomCardName.Silver);
            }
        }
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChosenCards = new ArrayList<DomCardName>();
        do {
            theChosenCards = new ArrayList<DomCardName>();
            owner.getEngine().getGameFrame().askToSelectCards("Trash", owner.getCardsInHand(), theChosenCards, 0);
        } while (theChosenCards.size()>2);
        for (int i = theChosenCards.size() - 1; i >= 0; i--) {
            for (DomCard theCard : owner.getCardsInHand()) {
                if (theChosenCards.get(i) == theCard.getName()) {
                    owner.trash(owner.removeCardFromHand(theCard));
                    owner.gain(DomCardName.Silver);
                    break;
                }
            }
        }
    }

}