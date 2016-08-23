package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class CountCard extends DomCard {
    public CountCard() {
      super( DomCardName.Count);
    }

    public void play() {
        //first look at trashing!
        if (owner.stillInEarlyGame()) {
            int theCrapCount=0;
            Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
            for (DomCard theCard : owner.getCardsInHand()) {
                if (theCard.getTrashPriority()<=DomCardName.Copper.getTrashPriority())
                    theCrapCount++;
            }
            if (theCrapCount>2) {
                if (owner.getCardsInHand().size() - theCrapCount == 1) {
                    owner.putOnTopOfDeck(owner.removeCardFromHand(owner.getCardsInHand().get(owner.getCardsInHand().size() - 1)));
                    trashHand();
                    return;
                } else {
                    if (owner.getCardsInHand().size() == theCrapCount) {
                        owner.gain(DomCardName.Copper);
                        trashHand();
                        return;
                    }
                }
            }
        }
        //then quick fix for Treasure Map
        if (!owner.getCardsFromHand(DomCardName.Treasure_Map).isEmpty() && owner.countInDeck(DomCardName.Treasure_Map)>1) {
            owner.putOnTopOfDeck(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Treasure_Map).get(0)));
            owner.addAvailableCoins(3);
            return;
        }
        //then maybe gain a Duchy discarding 2 cards
        DomCardName theBestDesiredCard = owner.getDesiredCard(owner.getTotalPotentialCurrency().add(new DomCost(3, 0)), false);
        Collections.sort(owner.getCardsInHand(),SORT_FOR_DISCARD_FROM_HAND);
        if (owner.wants(DomCardName.Duchy) && theBestDesiredCard==DomCardName.Duchy) {
            if (!owner.getCardsInHand().isEmpty())
                owner.discard(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
            if (!owner.getCardsInHand().isEmpty())
                owner.discard(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
            owner.gain(DomCardName.Duchy);
            return;
        }
        //then maybe put a good card back
        DomCard theBestCardToPutBack = null;
        for (int i=0;i<owner.getCardsInHand().size();i++) {
            DomCard theRemoved = owner.removeCardFromHand(owner.getCardsInHand().get(i));
            DomCardName theDesiredCard = owner.getDesiredCard(owner.getTotalPotentialCurrency().add(new DomCost(3, 0)), false);
            if (theBestDesiredCard==theDesiredCard) {
                theBestCardToPutBack = theRemoved;
            }
            owner.getCardsInHand().add(i,theRemoved);
        }
        if (theBestCardToPutBack!=null && theBestCardToPutBack.getDiscardPriority(1)>DomCardName.Copper.getDiscardPriority(1)) {
            owner.putOnTopOfDeck(owner.removeCardFromHand(theBestCardToPutBack));
            owner.addAvailableCoins(3);
            return;
        }
        //then maybe discard 2 cards
        DomCard theFirstRemoved = null;
        if (!owner.getCardsInHand().isEmpty())
            theFirstRemoved = owner.removeCardFromHand(owner.getCardsInHand().get(0));
        DomCard theSecondRemoved = null;
        if (!owner.getCardsInHand().isEmpty())
            theSecondRemoved = owner.removeCardFromHand(owner.getCardsInHand().get(0));
        if (theBestDesiredCard==owner.getDesiredCard(owner.getTotalPotentialCurrency().add(new DomCost(3, 0)), false)) {
            if (theFirstRemoved!=null)
                owner.discard(theFirstRemoved);
            if (theSecondRemoved!=null)
                owner.discard(theSecondRemoved);
            owner.addAvailableCoins(3);
            return;
        }
        if (theFirstRemoved!=null)
            owner.getCardsInHand().add(theFirstRemoved);
        if (theSecondRemoved!=null)
            owner.getCardsInHand().add(theSecondRemoved);
        //just gain a Copper and add $3
        owner.gain(DomCardName.Copper);
        owner.addAvailableCoins(3);
    }

    private void trashHand() {
        while (!owner.getCardsInHand().isEmpty())
            owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
    }
}