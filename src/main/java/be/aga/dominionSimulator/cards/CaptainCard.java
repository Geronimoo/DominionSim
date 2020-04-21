package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class CaptainCard extends DomCard {
    public CaptainCard() {
      super(DomCardName.Captain);
    }

    public void play() {
        DomCardName theChosenCard;
        theChosenCard = chooseCardToSteer();
        if (theChosenCard==null)
            return;
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " steers towards "+theChosenCard.toHTML());
        DomCard theCard = owner.getCurrentGame().takeFromSupply(theChosenCard);
        owner.handleUrchins(theCard);
        theCard.setOwner(owner);
        owner.playThis(theCard);
        theCard.setOwner(null);
        owner.getCurrentGame().returnToSupply(theCard);
    }

    private DomCardName chooseCardToSteer() {
        if (owner.isHumanOrPossessedByHuman()) {
            return handleHuman();
        }
        for (DomBuyRule theRule:owner.getBuyRules()) {
            DomCardName theCard = theRule.getCardToBuy();
            if (!theCard.hasCardType(DomCardType.Action))
                continue;
            if (theCard.hasCardType(DomCardType.Duration))
                continue;
            if (theCard.hasCardType(DomCardType.Reserve))
                continue;
            if (owner.getCurrentGame().countInSupply(theCard)==0)
                continue;
            if (theCard.getCost(owner.getCurrentGame()).customCompare(new DomCost(4,0))>0)
                continue;
            if (theCard.hasCardType(DomCardType.Terminal) && owner.getProbableActionsLeft()<=1)
                continue;
            if (theCard.hasCardType(DomCardType.Terminal) && !theCard.hasCardType(DomCardType.Card_Advantage))
                continue;
            if (theCard.hasCardType(DomCardType.Village)&& owner.getProbableActionsLeft()>0)
                continue;
            if (!theCard.hasCardType(DomCardType.Cycler))
                continue;
            return theCard;
        }
        return null;
    }

    private DomCardName handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
             if (new DomCost(4,0).customCompare(theCard.getCost(owner.getCurrentGame())) >= 0 && owner.getCurrentGame().countInSupply(theCard) > 0 && theCard.hasCardType(DomCardType.Action) && !theCard.hasCardType(DomCardType.Duration))
                    theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return null;
        return owner.getEngine().getGameFrame().askToSelectOneCard("Select card to Captain "+this.getName().toString(), theChooseFrom, "Mandatory!");
    }

    @Override
    public void resolveDuration() {
        play();
    }
}