package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LurkerCard extends DomCard {
    public LurkerCard() {
      super( DomCardName.Lurker);
    }

  public void play() {
    owner.addActions(1);
    if (owner.isHumanOrPossessedByHuman()){
        handleHuman();
        return;
    }
    DomCard theWantedAction = findActionInTrash();
    if (theWantedAction!=null && theWantedAction.getName()!=DomCardName.Hunting_Grounds ) {
      owner.gain(owner.getCurrentGame().removeFromTrash(theWantedAction));
    } else {
      trashActionFromSupply();
    }
  }

    private void handleHuman() {
        if (!owner.getCurrentGame().getTrashedCards().isEmpty()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCurrentGame().getTrashedCards()) {
               if (theCard.hasCardType(DomCardType.Action))
                   theChooseFrom.add(theCard.getName());
            }
            DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Gain from Trash?", theChooseFrom, "Don't gain from trash");
            if (theChosenCard!=null) {
                owner.gain(owner.getCurrentGame().removeFromTrash(theChosenCard));
                return;
            }
        }
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theCard.hasCardType(DomCardType.Action) && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        owner.trash(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Trash:", theChooseFrom, "Mandatory!")));
    }

    @Override
    public boolean wantsToBePlayed() {
        if (owner.getPlayStrategyFor(this)==DomPlayStrategy.playOnlyIfGaining && owner.getCardsFromHand(DomCardName.Lurker).size()>1)
            return true;
        if (owner.getPlayStrategyFor(this)==DomPlayStrategy.playOnlyIfGaining && findActionInTrash()==null)
            return false;
        return true;
    }

    private void trashActionFromSupply() {
        for (DomBuyRule theBuyRule : owner.getBuyRules()) {
            if (!owner.getCurrentGame().getBoard().isFromSeparatePile(theBuyRule.getCardToBuy()) && theBuyRule.getCardToBuy().hasCardType(DomCardType.Action) && owner.checkBuyConditions(theBuyRule) && owner.getCurrentGame().countInSupply(theBuyRule.getCardToBuy())>0) {
                DomCard theCard = owner.getCurrentGame().takeFromSupply(theBuyRule.getCardToBuy());
                owner.trash(theCard);
                return;
            }
        }
        for (DomBuyRule theBuyRule : owner.getBuyRules()) {
            if (!owner.getCurrentGame().getBoard().isFromSeparatePile(theBuyRule.getCardToBuy()) && theBuyRule.getCardToBuy().hasCardType(DomCardType.Action) && owner.getCurrentGame().countInSupply(theBuyRule.getCardToBuy())>0) {
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
                    if (theCard.getName()==theBuyRule.getCardToBuy() && owner.checkBuyConditions(theBuyRule))
                        return theCard;
                }
            }
        }
        return null;
    }
}