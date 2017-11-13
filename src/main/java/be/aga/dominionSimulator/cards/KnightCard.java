package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class KnightCard extends DomCard {
    public KnightCard(DomCardName aName) {
        super(aName);
    }

    public void play() {
        //theOwner needed in multiplayer game when this card will be trashed mid-play
        DomPlayer theOwner = owner;
        for (DomPlayer thePlayer : theOwner.getOpponents()) {
            if (thePlayer.checkDefense())
                continue;
            ArrayList<DomCard> theRevealedCards = thePlayer.revealTopCards(2);
            if (theRevealedCards.isEmpty())
                continue;
            DomCard theCardToTrash = null;
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
            Collections.sort(theCardsToConsider, SORT_FOR_TRASHING);
            if (theCardsToConsider.isEmpty())
                continue;
            if (thePlayer.isHuman() && theCardsToConsider.size()>1) {
                theCardToTrash = handleHuman(theCardsToConsider);
            } else {
                theCardToTrash = theCardsToConsider.remove(0);
            }
            thePlayer.trash(theCardToTrash);
            if (theCardToTrash.hasCardType(DomCardType.Knight) && owner != null && !owner.getCardsFromPlay(getName()).isEmpty())
                if (!owner.getCardsFromPlay(getName()).isEmpty())
                  owner.trash(owner.removeCardFromPlay(this));
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