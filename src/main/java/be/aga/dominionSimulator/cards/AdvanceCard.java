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
        owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(DomCardType.Action).get(0)));
        DomCardName theCardToGain = owner.getDesiredCard(DomCardType.Action, new DomCost(6, 0), false,false,null);
        if (theCardToGain==null)
            theCardToGain=owner.getCurrentGame().getBestCardInSupplyFor(owner,DomCardType.Action,new DomCost(6,0));
        if (theCardToGain != null)
            owner.gain(theCardToGain);
    }
}