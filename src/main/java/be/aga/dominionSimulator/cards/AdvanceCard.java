package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class AdvanceCard extends DomCard {
    public AdvanceCard() {
      super( DomCardName.Advance);
    }

    public void play() {
        Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
        if (owner.getCardsFromHand(DomCardType.Action).isEmpty()) {
            if (DomEngine.haveToLog) DomEngine.addToLog( owner + " has no actions in hand so " + this +" does nothing");
            return;
        }
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(DomCardType.Action).get(0)));
        DomCardName theCardToGain = owner.getDesiredCard(DomCardType.Action, new DomCost(6, 0), false,false,null);
        if (theCardToGain==null)
            theCardToGain=owner.getCurrentGame().getBestCardInSupplyFor(owner,DomCardType.Action,new DomCost(6,0));
        if (theCardToGain != null)
            owner.gain(theCardToGain);
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            if (theCard.hasCardType(DomCardType.Action))
                theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
        owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theCard.hasCardType(DomCardType.Action) && new DomCost(6,0).customCompare(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for " + this.getName().toString(), theChooseFrom, "Mandatory!")));
    }
}