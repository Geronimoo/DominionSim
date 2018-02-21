package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.*;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.awt.peer.ChoicePeer;
import java.util.ArrayList;
import java.util.Collections;

public class LocustsCard extends DomCard {
    public LocustsCard() {
      super( DomCardName.Locusts);
    }

    public void play() {
        ArrayList<DomCard> theCards = owner.revealTopCards(1);
        if (theCards.isEmpty())
            return;
        owner.trash(theCards.get(0));
        if (theCards.get(0).getName() == DomCardName.Copper || theCards.get(0).getName() == DomCardName.Estate) {
            owner.gain(DomCardName.Curse);
        } else {
            if (owner.isHumanOrPossessedByHuman()) {
                handleHuman(theCards.get(0));
            } else {
                DomCard theTrashedCard = theCards.get(0);
                ArrayList<DomCardName> theChooseFrom = new ArrayList<>();
                for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
                    if (theTrashedCard.getCost(owner.getCurrentGame()).customCompare(theCard.getCost(owner.getCurrentGame())) > 0
                            && owner.getCurrentGame().countInSupply(theCard) > 0
                            && theCard.sharesTypeWith(theTrashedCard))
                        theChooseFrom.add(theCard);
                }
                if (theChooseFrom.isEmpty())
                    return;
                for (DomBuyRule theRule : owner.getBuyRules()) {
                    DomCardName cardToBuy = theRule.getCardToBuy();
                    for (DomCardName theChoice : theChooseFrom) {
                        if (cardToBuy == theChoice) {
                            boolean wantsToGain = true;
                            for (DomBuyCondition theCondition : theRule.getBuyConditions()) {
                                if (!theCondition.isTrue(owner.getPossessor() != null ? owner.getPossessor() : owner)) {
                                    wantsToGain = false;
                                    break;
                                }
                            }
                            if (wantsToGain && !owner.suicideIfBuys(cardToBuy)) {
                                owner.gain(theChoice);
                                return;
                            }
                        }
                    }
                }
                Collections.sort(theChooseFrom,DomCardName.FOR_TRASHING);
                owner.gain(theChooseFrom.get(theChooseFrom.size()-1));
            }
        }
    }

    private void handleHuman(DomCard theTrashedCard) {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theTrashedCard.getCost(owner.getCurrentGame()).customCompare(theCard.getCost(owner.getCurrentGame())) > 0
                    && owner.getCurrentGame().countInSupply(theCard)>0
                    && theCard.sharesTypeWith(theTrashedCard))
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        if (theChooseFrom.size()==1) {
            owner.gain(owner.getCurrentGame().takeFromSupply(theChooseFrom.get(0)));
        } else {
            owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!")));
        }
    }

}