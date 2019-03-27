package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class ImproveCard extends DomCard {
    public ImproveCard() {
      super( DomCardName.Improve);
    }

    @Override
    public void play() {
        owner.addAvailableCoins(2);
        owner.increaseImprovePlayedCounter();
    }

    public static void improveSomething(DomPlayer owner) {
        ArrayList<DomCard> theActions = owner.getCardsFromPlay(DomCardType.Action);
        ArrayList<DomCard> theChooseFrom = new ArrayList<>();
        for (DomCard theAction:theActions) {
            if (theAction.discardAtCleanUp())
                theChooseFrom.add(theAction);
        }
        if (theChooseFrom.isEmpty())
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            DomCard theCardToImprove = owner.getEngine().getGameFrame().askToSelectOneCardWithDomCard("Select card to " + DomCardName.Improve.toHTML(), theChooseFrom, "Don't Improve anything!");
            if (theCardToImprove==null)
                return;
            ArrayList<DomCardName> theChooseAgain = new ArrayList<DomCardName>();
            for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
                if (theCard.getCost(owner.getCurrentGame()).customCompare(theCardToImprove.getCost(owner.getCurrentGame()).add(new DomCost(1,0)))==0
                  && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseAgain.add(theCard);
            }
            owner.trash(owner.removeCardFromPlay(theCardToImprove));
            if (theChooseAgain.isEmpty())
                return;
            if (theChooseAgain.size()==1) {
                owner.gain(owner.getCurrentGame().takeFromSupply(theChooseAgain.get(0)));
            } else {
                owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Gain a card for " + DomCardName.Improve.toHTML(), theChooseAgain, "Mandatory!")));
            }
        } else {
            for (DomCard theCard:theChooseFrom) {
                DomCardName theDesiredCard = owner.getDesiredCard(theCard.getName().getCost(owner.getCurrentGame()).add(new DomCost(1, 0)), true);
                if (theDesiredCard==null)
                    continue;
                owner.trash(owner.removeCardFromPlay(theCard));
                owner.gain(theDesiredCard);
                break;
            }
        }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism) && owner.getCurrentGame().getActivePlayer()==owner)
            return true;
        return super.hasCardType(aType);
    }

}