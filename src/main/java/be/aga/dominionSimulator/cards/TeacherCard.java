package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyCondition;
import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomBotFunction;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

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

    @Override
    public boolean wantsToBeMultiplied() {
        return false;
    }
}