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
        DomCardName theChosenCard;
        boolean chooseNonTerminal=false;
        if (owner.getActionsAndVillagersLeft()==0 && !owner.getCardsFromHand(DomCardType.Action).isEmpty())
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
        owner.handleUrchins(theCopy);
        owner.play(theCopy);
    }

    private DomCardName chooseCardToShapeShift(boolean chooseNonTerminal) {
        if (owner.isHumanOrPossessedByHuman()) {
            return handleHuman();
        }
        for (DomBuyRule theRule:owner.getBuyRules()) {
            DomCardName theCard = theRule.getCardToBuy();
            if (!theCard.hasCardType(DomCardType.Action))
                continue;
            if (theCard.hasCardType(DomCardType.Reserve))
                continue;
            if (owner.getCurrentGame().countInSupply(theCard)==0)
                continue;
            if (theCard.hasCardType(DomCardType.Terminal) && chooseNonTerminal)
                continue;
            ArrayList< DomCard > theTerminalsInHand = owner.getCardsFromHand( DomCardType.Terminal );
            int i=owner.getActionsAndVillagersLeft()-theTerminalsInHand.size();
            if (theCard.hasCardType(DomCardType.Village)&&i>0)
                continue;
            if (getName()==DomCardName.Band_of_Misfits)
                if (theCard.getCost(owner.getCurrentGame()).customCompare(getCost(owner.getCurrentGame()))>=0)
                    continue;
            if (getName()==DomCardName.Overlord)
                if (new DomCost(5,0).customCompare(theCard.getCost(owner.getCurrentGame()))<0)
                    continue;
            return theCard;
        }
        return null;
    }

    private DomCardName handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (getName()==DomCardName.Overlord) {
                if (new DomCost(5,0).customCompare(theCard.getCost(owner.getCurrentGame())) >= 0 && owner.getCurrentGame().countInSupply(theCard) > 0 && theCard.hasCardType(DomCardType.Action))
                    theChooseFrom.add(theCard);
            } else {
                if (getCost(owner.getCurrentGame()).customCompare(theCard.getCost(owner.getCurrentGame())) > 0 && owner.getCurrentGame().countInSupply(theCard) > 0 && theCard.hasCardType(DomCardType.Action))
                    theChooseFrom.add(theCard);
            }
        }
        if (theChooseFrom.isEmpty())
            return null;
        return owner.getEngine().getGameFrame().askToSelectOneCard("Select card to immitate for "+this.getName().toString(), theChooseFrom, "Mandatory!");
    }
}