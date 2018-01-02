package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import java.util.ArrayList;
import java.util.Collections;

public class RazeCard extends DomCard {
    public RazeCard() {
      super(DomCardName.Raze);
    }

    public void play() {
        DomPlayer theOwner = owner;
        owner.addActions(1);
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
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

    private void handleHuman() {
        DomPlayer theOwner = owner;
        owner.setNeedsToUpdate();
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Trash Raze itself");
        if (theChosenCard!=null) {
            owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
        } else {
            theChosenCard=this.getName();
            if (owner.getCardsInPlay().contains(this))
              owner.trash(owner.removeCardFromPlay(this));
            else
              return;
        }
        int theAmount = theChosenCard.getCoinCost(theOwner.getCurrentGame());
        if (theAmount==0)
            return;
        ArrayList<DomCard> theRevealedCards = theOwner.revealTopCards(theAmount);
        if (theRevealedCards.isEmpty())
            return;
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : theRevealedCards) {
            theChooseFrom.add(theCard.getName());
        }
        theChosenCard = theOwner.getEngine().getGameFrame().askToSelectOneCard("Choose a card", theChooseFrom, "Mandatory!");
        for (DomCard theCard : theRevealedCards) {
            if (theCard.getName()==theChosenCard) {
                theOwner.addCardToHand(theRevealedCards.remove(theRevealedCards.indexOf(theCard)));
                break;
            }
        }
        theOwner.discard(theRevealedCards);
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