package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class RebuildCard extends DomCard {
    public RebuildCard() {
      super( DomCardName.Rebuild);
    }

    public void play() {
        owner.addActions(1);
        DomCardName theNamedCard;
        if (owner.countInDeck(DomCardName.Estate) - owner.getCardsFromHand(DomCardName.Estate).size() > 0
                && owner.getCurrentGame().countInSupply(DomCardName.Duchy) > 0
                && owner.countInDeck(DomCardName.Province)-owner.getCardsFromHand(DomCardName.Province).size()==0) {
            theNamedCard = DomCardName.Duchy;
        } else {
            theNamedCard = DomCardName.Province;
        }
        if (owner.countInDeck(DomCardName.Duchy)==0 && owner.getCurrentGame().countInSupply(DomCardName.Duchy)==0 && owner.getCurrentGame().countInSupply(DomCardName.Province)>0)
            theNamedCard=DomCardName.Estate;

        if (owner.getCurrentGame().countInSupply(DomCardName.Colony)>0)
            theNamedCard=DomCardName.Colony;

        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " names " + theNamedCard);

        ArrayList<DomCard> theRevealedCards = owner.revealUntilVictoryCardNotNamed(theNamedCard);
        if (theRevealedCards.isEmpty())
            return;
        DomCard theLastCard = theRevealedCards.get(theRevealedCards.size() - 1);
        if (theLastCard.hasCardType(DomCardType.Victory) && theLastCard.getName() != theNamedCard) {
            DomCost theCardCost = theLastCard.getCost(owner.getCurrentGame());
            owner.trash(theRevealedCards.remove(theRevealedCards.size() - 1));
            DomCardName theDesiredCard = owner.getDesiredCard(DomCardType.Victory, theCardCost.add(new DomCost(3, 0)), false, false, null);
            if (theDesiredCard == null) {
                theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, DomCardType.Victory, theCardCost.add(new DomCost(3, 0)));
            }
            if (theDesiredCard != null) {
                owner.gain(theDesiredCard);
            } else {
                if (DomEngine.haveToLog) DomEngine.addToLog( owner + " gains nothing");
            }
        }
        owner.discard(theRevealedCards);
    }
}