package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class CardinalCard extends DomCard {
    public CardinalCard() {
        super(DomCardName.Cardinal);
    }

    public void play() {
        owner.addAvailableCoins(2);
        //theOwner needed in multiplayer game when this card will be trashed mid-play
        DomPlayer theOwner = owner;
        for (DomPlayer thePlayer : theOwner.getOpponents()) {
            if (thePlayer.checkDefense())
                continue;
            ArrayList<DomCard> theRevealedCards = thePlayer.revealTopCards(2);
            if (theRevealedCards.isEmpty())
                continue;
            DomCard theCardToExile = null;
            ArrayList<DomCard> theCardsToConsider = new ArrayList<DomCard>();
            ArrayList<DomCard> theCardsToDiscard = new ArrayList<DomCard>();
            for (DomCard theCard : theRevealedCards) {
                if (theCard.getCoinCost(theOwner.getCurrentGame()) < 3 || theCard.getCoinCost(theOwner.getCurrentGame()) > 6 || theCard.getPotionCost() > 0 || theCard.getDebtCost() > 0)
                    theCardsToDiscard.add(theCard);
                else
                    theCardsToConsider.add(theCard);
            }
            theRevealedCards=null;
            thePlayer.discard(theCardsToDiscard);
            theCardsToDiscard=null;
            Collections.sort(theCardsToConsider, SORT_FOR_DISCARDING);
            if (theCardsToConsider.isEmpty())
                continue;
            if (thePlayer.isHuman() && theCardsToConsider.size()>1) {
                theCardToExile = handleHuman(theCardsToConsider);
            } else {
                theCardToExile = theCardsToConsider.remove(0);
            }
            thePlayer.exile(theCardToExile);
            if (!theCardsToConsider.isEmpty())
                thePlayer.discard(theCardsToConsider);
        }
    }

    private DomCard handleHuman(ArrayList<DomCard> theRevealedCards) {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : theRevealedCards) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
        for (DomCard theCard : theRevealedCards) {
            if (theCard.getName()==theChosenCard)
                return theRevealedCards.remove(theRevealedCards.indexOf(theCard));
        }
        return null;
    }
}