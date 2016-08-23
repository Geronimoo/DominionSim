package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class TradeCard extends DomCard {
    public TradeCard() {
      super( DomCardName.Trade);
    }

    public void play() {
        if (owner.getCardsInHand().isEmpty())
            return;
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
}