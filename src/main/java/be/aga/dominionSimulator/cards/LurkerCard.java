package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.HashSet;
import java.util.Set;

public class LurkerCard extends DomCard {
    public LurkerCard() {
      super( DomCardName.Lurker);
    }

  public void play() {
    owner.addActions(1);
    DomCard theWantedAction = findActionInTrash();
    if (theWantedAction!=null) {
      owner.gain(owner.getCurrentGame().removeFromTrash(theWantedAction));
    } else {
      trashActionFromSupply();
    }
  }

    private void trashActionFromSupply() {
        for (DomBuyRule theBuyRule : owner.getBuyRules()) {
            if (theBuyRule.getCardToBuy().hasCardType(DomCardType.Action) && owner.checkBuyConditions(theBuyRule)) {
                DomCard theCard = owner.getCurrentGame().takeFromSupply(theBuyRule.getCardToBuy());
                owner.trash(theCard);
                return;
            }
        }
        for (DomBuyRule theBuyRule : owner.getBuyRules()) {
            if (theBuyRule.getCardToBuy().hasCardType(DomCardType.Action) ) {
                DomCard theCard = owner.getCurrentGame().takeFromSupply(theBuyRule.getCardToBuy());
                owner.trash(theCard);
                return;
            }
        }
    }

    private DomCard findActionInTrash() {
        if (owner.getCurrentGame().getTrashedCards().isEmpty())
            return null;
        Set<DomCardName> theActions = new HashSet<DomCardName>();
        for (DomCard theCard : owner.getCurrentGame().getTrashedCards()){
            if (theCard.hasCardType(DomCardType.Action) && owner.wantsToGainOrKeep(theCard.getName()))
                theActions.add(theCard.getName());
        }
        if (theActions.isEmpty())
            return null;
        for (DomBuyRule theBuyRule : owner.getBuyRules()) {
            if (theActions.contains(theBuyRule.getCardToBuy())){
                for (DomCard theCard : owner.getCurrentGame().getTrashedCards()) {
                    if (theCard.getName()==theBuyRule.getCardToBuy())
                        return theCard;
                }
            }
        }
        return null;
    }
}