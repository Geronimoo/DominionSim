package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class InvestCard extends DomCard {
    public InvestCard() {
        super(DomCardName.Invest);
    }

    public void play() {
        DomCardName theDesiredAction = owner.getDesiredCard(DomCardType.Action, new DomCost(1000, 1000), false, false, null);
        if (theDesiredAction==null) {
            theDesiredAction = owner.getCurrentGame().getBestCardInSupplyFor(owner,DomCardType.Action,new DomCost(1000,1000),false);
        }
        if (owner.isHumanOrPossessedByHuman()) {
            theDesiredAction = handleHuman();
        }
        if (theDesiredAction==null)
            return;
        DomCard theAction = owner.getCurrentGame().takeFromSupply(theDesiredAction);
        owner.getDeck().addPhysicalCardWhenNotGained(theAction);
        owner.moveToExileMat(theAction);
        owner.addInvestment(theAction);
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (thePlayer.hasInvestedIn(theDesiredAction)) {
                if (DomEngine.haveToLog) DomEngine.addToLog( thePlayer + " has Invested in " +theDesiredAction.toHTML());
                thePlayer.drawCards(thePlayer.countInvestmentsIn(theAction.getName())*2);
            }
        }
    }

    private DomCardName handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theCard.hasCardType(DomCardType.Action) && owner.getCurrentGame().countInSupply(theCard) > 0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return null;
        return owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for " + this.getName().toString(), theChooseFrom, "Mandatory!");
    }
}