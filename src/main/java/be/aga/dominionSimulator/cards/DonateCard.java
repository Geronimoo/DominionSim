package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class DonateCard extends DomCard {
    public DonateCard() {
      super( DomCardName.Donate);
    }

    public void play() {
        owner.triggerDonateAfterTurn();
   }
    
    public static void trashStuff(DomPlayer owner) {
        owner.addCardsToHand(owner.removeAllCardsFromDiscardAndDeck());
        Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " triggers " + DomCardName.Donate.toHTML());
        owner.showHand();
        while (!owner.getCardsInHand().isEmpty()) {
            DomCard theCardToTrash=owner.getCardsInHand().get( 0 );
            if (theCardToTrash.getTrashPriority()>DomCardName.Copper.getTrashPriority()
                    || owner.getTotalMoneyInDeck()-theCardToTrash.getPotentialCoinValue() < 4 ) {
                break;
            }
            owner.trash(owner.removeCardFromHand( theCardToTrash));
        }
        owner.addAllToDeck(owner.getCardsInHand());
        owner.clearCardsInHand();
        owner.shuffleDeck();
        owner.drawCards(5);
    }
}