package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class BatCard extends DomCard {
    private boolean exchangeForVampire;

    public BatCard() {
      super( DomCardName.Bat);
    }
    
    public void play() {
        exchangeForVampire=false;
        owner.setNeedsToUpdateGUI();
        if (owner.getCardsInHand().isEmpty())
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHumanPlayer();
        } else {
            int trashCount = 0;
            Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
            while (trashCount<2 && !owner.getCardsInHand().isEmpty()) {
                if (owner.getCardsInHand().get(0).getTrashPriority() <= DomCardName.Copper.getTrashPriority()) {
                    owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
                    trashCount++;
                    exchangeForVampire=true;
                } else {
                    break;
                }
           }
        }
    }

    private void handleHumanPlayer() {
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("Choose to trash" , owner.getCardsInHand(), theChosenCards, 0);
        if (theChosenCards.isEmpty())
            return;
        exchangeForVampire = true;
        while (!theChosenCards.isEmpty()) {
            DomCard theCardToTrash = null;
            for (DomCard theCard : owner.getCardsInHand()) {
                if (theCard.getName() == theChosenCards.get(0).getName())
                    theCardToTrash = theCard;
            }
            theChosenCards.remove(0);
            owner.trash(owner.removeCardFromHand(theCardToTrash));
            exchangeForVampire=true;
        }
    }

    @Override
    public void handleCleanUpPhase() {
        if (!exchangeForVampire) {
            super.handleCleanUpPhase();
        } else {
            DomPlayer theOwner = owner;
            DomCard theVampire= owner.getCurrentGame().takeFromSupply(DomCardName.Vampire);
            if (theVampire == null)
                return;
            owner.returnToSupply(this);
            theOwner.getDeck().addPhysicalCardWhenNotGained(theVampire);
            theOwner.getDeck().justAddToDiscard(theVampire);
        }
    }
}