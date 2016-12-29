package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class RazeCard extends DomCard {
    public RazeCard() {
      super(DomCardName.Raze);
    }

    public void play() {
        DomPlayer theOwner = owner;
        owner.addActions(1);
        if (owner.getCardsInHand().isEmpty()) {
            trashItself(theOwner);
            return;
        }

        Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
        DomCard theCardToTrash = owner.getCardsInHand().get(0);
        if (!owner.removingReducesBuyingPower(theCardToTrash)) {
            int theAmount = theCardToTrash.getCoinCost(owner.getCurrentGame());
            owner.trash(owner.removeCardFromHand(theCardToTrash));
            revealAndKeepACard(owner,theAmount);
            return;
        }
        if (!owner.getCurrentGame().getBoard().getTrashedCards().contains(this))
          trashItself(theOwner);
    }

    private void trashItself(DomPlayer theOwner) {
        if (owner.getCardsFromPlay(getName()).contains(this)) {
            owner.trash(owner.removeCardFromPlay(this));
            revealAndKeepACard(theOwner, getCoinCost(theOwner.getCurrentGame()));
        }
    }

    private void revealAndKeepACard(DomPlayer anOwner, int anAmount) {
        if (anAmount==0)
            return;
        ArrayList<DomCard> theRevealedCards = anOwner.revealTopCards(anAmount);
        if (theRevealedCards.isEmpty())
            return;
        Collections.sort(theRevealedCards,SORT_FOR_DISCARD_FROM_HAND);
        anOwner.addCardToHand(theRevealedCards.remove(theRevealedCards.size()-1));
        anOwner.discard(theRevealedCards);
    }
}