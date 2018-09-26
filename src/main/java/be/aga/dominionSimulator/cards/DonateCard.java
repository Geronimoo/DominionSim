package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class DonateCard extends DomCard {
    public DonateCard() {
        super(DomCardName.Donate);
    }

    public void play() {
        owner.triggerDonateAfterTurn();
    }

    public static void trashStuff(DomPlayer owner) {
        owner.addCardsToHand(owner.removeAllCardsFromDiscardAndDeck());
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman(owner);
        } else {
            handleNormalBot(owner);
        }
        owner.addAllToDeck(owner.getCardsInHand());
        owner.clearCardsInHand();
        owner.shuffleDeck();
        owner.drawCards(5);
    }

    private static void handleNormalBot(DomPlayer owner) {
        Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
        if (DomEngine.haveToLog) DomEngine.addToLog(owner + " triggers " + DomCardName.Donate.toHTML());
        owner.showHand();
        while (!owner.getCardsInHand().isEmpty()) {
            DomCard theCardToTrash = owner.getCardsInHand().get(0);
            if (theCardToTrash.getTrashPriority() > DomCardName.Copper.getTrashPriority()
                    || owner.getTotalMoneyInDeck() - theCardToTrash.getPotentialCoinValue() < 6) {
                break;
            }
            owner.trash(owner.removeCardFromHand(theCardToTrash));
            Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
        }
    }

    private static void handleHuman(DomPlayer owner) {
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("Choose cards to trash", owner.getCardsInHand(), theChosenCards, 0);
        for (DomCard theCardName : theChosenCards) {
            owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theCardName.getName()).get(0)));
        }
    }
}