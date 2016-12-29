package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class ShapeshifterCard extends DomCard {
    public ShapeshifterCard(DomCardName aCardName) {
      super( aCardName );
    }

    public void play() {
        DomCardName theChosenCard = null;
        boolean chooseNonTerminal=false;
        if (owner.getActionsLeft()==0 && !owner.getCardsFromHand(DomCardType.Action).isEmpty())
          chooseNonTerminal=true;
        theChosenCard = chooseCardToShapeShift(chooseNonTerminal);
        if (theChosenCard==null && chooseNonTerminal) {
            theChosenCard=chooseCardToShapeShift(false);
        }
        if (theChosenCard==null)
            return;
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " chooses to play " + this +" as "+theChosenCard.toHTML());
        if (!owner.getCardsFromPlay(this.getName()).isEmpty())
            owner.removeCardFromPlay(this);
        DomCard theCopy = theChosenCard.createNewCardInstance();
        theCopy.setOwner(owner);
        theCopy.setShapeshifterCard(this);
        //bug fix when throned
        for (DomCard theCard : owner.getCardsInPlay()){
            if (theCard.getShapeshifterCard()==this) {
                owner.removeCardFromPlay(theCard);
                break;
            }
        }
        owner.play(theCopy);
    }

    private DomCardName chooseCardToShapeShift(boolean chooseNonTerminal) {
        for (DomBuyRule theRule:owner.getBuyRules()) {
            DomCardName theCard = theRule.getCardToBuy();
            if (!theCard.hasCardType(DomCardType.Action))
                continue;
            if (owner.getCurrentGame().countInSupply(theCard)==0)
                continue;
            if (theCard.hasCardType(DomCardType.Terminal) && chooseNonTerminal)
                continue;
            ArrayList< DomCard > theTerminalsInHand = owner.getCardsFromHand( DomCardType.Terminal );
            int i=owner.getActionsLeft()-theTerminalsInHand.size();
            if (theCard.hasCardType(DomCardType.Village)&&i>0)
                continue;
            if (getName()==DomCardName.Band_of_Misfits)
                if (theCard.getCost(owner.getCurrentGame()).compareTo(getCost(owner.getCurrentGame()))>=0)
                    continue;
            if (getName()==DomCardName.Overlord)
                if (new DomCost(5,0).compareTo(theCard.getCost(owner.getCurrentGame()))<0)
                    continue;
            return theCard;
        }
        return null;
    }
}