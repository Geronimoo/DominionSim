package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class HermitCard extends DomCard {
    public HermitCard() {
      super( DomCardName.Hermit);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
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

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsFromDiscard()) {
            if (!theCard.hasCardType(DomCardType.Treasure))
                theChooseFrom.add(theCard.getName());
        }
        if (!theChooseFrom.isEmpty() && owner.getEngine().getGameFrame().askPlayer("<html>Trash from discard :" + theChooseFrom +" ?</html>", "Resolving " + this.getName().toString())) {
            owner.trash(owner.removeCardFromDiscard(owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!")));
        } else {
            theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsInHand()) {
                if (!theCard.hasCardType(DomCardType.Treasure))
                    theChooseFrom.add(theCard.getName());
            }
            if (!theChooseFrom.isEmpty()) {
                DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card ?", theChooseFrom, "Don't trash");
                if (theChosenCard != null) {
                    owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
                }
            }
        }
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
            if (new DomCost(3,0).compareTo(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!")));
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