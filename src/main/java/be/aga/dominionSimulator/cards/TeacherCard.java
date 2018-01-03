package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyCondition;
import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomBotFunction;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;

public class TeacherCard extends DomCard {
    public TeacherCard() {
      super( DomCardName.Teacher);
    }

    public void play() {
      if (owner.getCardsInPlay().contains(this))
        owner.putOnTavernMat(owner.removeCardFromPlay(this));
    }

    @Override
    public void doWhenCalled() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        for (DomBuyRule theRule : owner.getBuyRules()) {
            if (theRule.getCardToBuy()!=DomCardName.Teacher)
                continue;
            for (DomBuyCondition theCondition : theRule.getBuyConditions()) {
                switch (theCondition.getLeftFunction()) {
                    case isPlusOneActionTokenSet:
                        if (!owner.isPlusOneActionTokenSet()) {
                            owner.placePlusOneActionToken();
                            return;
                        }
                        break;
                    case isPlusOneCardTokenSet:
                        if (!owner.isPlusOneCardTokenSet()) {
                            owner.placePlusOneCardToken();
                            return;
                        }
                        break;
                    case isPlusOneCoinTokenSet:
                        if (!owner.isPlusOneCoinTokenSet()) {
                            owner.placePlusOneCoinToken();
                            return;
                        }
                        break;
                    case isPlusOneBuyTokenSet:
                        if (!owner.isPlusOneBuyTokenSet()){
                            owner.placePlusOneBuyToken();
                            return;
                        }
                        break;
                }
            }
        }
    }

    private void handleHuman() {
        ArrayList<String> theOptions = new ArrayList<String>();
        theOptions.add("+Action token");
        theOptions.add("+Card token");
        theOptions.add("+Coin token");
        theOptions.add("+Buy token");
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Place a token", theOptions, "Mandatory!");
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (!owner.cardHasToken(theCard))
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select a card", theChooseFrom, "Mandatory!");
        if (theChoice==0)
            owner.placePlusOneActionToken(theChosenCard);
        if (theChoice==1)
            owner.placePlusOneCardToken(theChosenCard);
        if (theChoice==2)
            owner.placePlusOneCoinToken(theChosenCard);
        if (theChoice==3)
            owner.placePlusOneBuyToken(theChosenCard);
    }

    @Override
    public boolean wantsToBeMultiplied() {
        return false;
    }
}