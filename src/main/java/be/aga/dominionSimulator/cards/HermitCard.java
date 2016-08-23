package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HermitCard extends DomCard {
    public HermitCard() {
      super( DomCardName.Hermit);
    }

    public void play() {
        if (owner.getPlayStrategyFor(this) != DomPlayStrategy.MarketSquareCombo || moreThanOneCardToTrash()) {
            maybeTrashACard();
        }else {
            if (owner.getPlayStrategyFor(this) == DomPlayStrategy.MarketSquareCombo && owner.getCardsFromHand(DomCardName.Market_Square).size() > 4)
                maybeTrashACard();
        }
        DomCardName theCardToGain = owner.getDesiredCard(new DomCost(3, 0), false);
        if (theCardToGain==null) {
            theCardToGain = owner.getCurrentGame().getBestCardInSupplyFor(owner,null,new DomCost(3,0));
        }
        owner.gain(theCardToGain);
    }

    private boolean moreThanOneCardToTrash() {
        int theCount = 0;
        for (DomCard theCard : owner.getDeck().getAllCards()) {
            if (theCard.getTrashPriority()<=DomCardName.Copper.getTrashPriority() && !theCard.hasCardType(DomCardType.Treasure))
                theCount++;
        }
        return theCount>1;
    }

    private void maybeTrashACard() {
        ArrayList<DomCard> theDiscard = owner.getCardsFromDiscard();
        DomCard theDiscardCardToTrash = null;
        if (!theDiscard.isEmpty()) {
            Collections.sort(theDiscard, SORT_FOR_TRASHING);
            int i = 0;
            while (i < theDiscard.size() && theDiscard.get(i).hasCardType(DomCardType.Treasure) ) {
                i++;
            }
            if (i < theDiscard.size() && theDiscard.get(i).getTrashPriority() <= DomCardName.Copper.getTrashPriority(owner))
                theDiscardCardToTrash = theDiscard.get(i);
        }
        DomCard theHandCardToTrash = null;
        ArrayList<DomCard> theCardsInHand = owner.getCardsInHand();
        if (!theCardsInHand.isEmpty()) {
            Collections.sort(theCardsInHand, SORT_FOR_TRASHING);
            int i = 0;
            while (i < theCardsInHand.size() && theCardsInHand.get(i).hasCardType(DomCardType.Treasure) ) {
                i++;
            }
            if (i < theCardsInHand.size()
                    && (theCardsInHand.get(i).getTrashPriority() <= DomCardName.Copper.getTrashPriority(owner) || (owner.getPlayStrategyFor(this)==DomPlayStrategy.MarketSquareCombo) && !owner.getCardsFromHand(DomCardName.Madman).isEmpty() && owner.getCardsFromHand(DomCardName.Market_Square).size()>4))
                theHandCardToTrash = theCardsInHand.get(i);
        }
        DomCard theCardToTrash = null;
        if (theHandCardToTrash!=null && theDiscardCardToTrash!=null) {
            if (theHandCardToTrash.getTrashPriority() < theDiscardCardToTrash.getTrashPriority())
                theCardToTrash=theHandCardToTrash;
            else
                theCardToTrash=theDiscardCardToTrash;
        }
        if (theHandCardToTrash!=null && theDiscardCardToTrash==null)
            theCardToTrash=theHandCardToTrash;
        if (theHandCardToTrash==null && theDiscardCardToTrash!=null)
            theCardToTrash=theDiscardCardToTrash;

        if (theCardToTrash!=null) {
            if (theCardToTrash==theDiscardCardToTrash)
                owner.trash(owner.removeCardFromDiscard(theDiscardCardToTrash));
            if (theCardToTrash==theHandCardToTrash)
                owner.trash(owner.removeCardFromHand(theCardToTrash));
        } else {
            if (DomEngine.haveToLog) DomEngine.addToLog( owner + " trashes nothing");
        }
    }

    @Override
    public int getPlayPriority() {
        if (owner.getPlayStrategyFor(this) == DomPlayStrategy.bigTurnBridge) {
            if (owner.getCardsInHand().size()<6)
                return DomCardName.Bridge.getPlayPriority()-1;
        }
        return super.getPlayPriority();
    }
}