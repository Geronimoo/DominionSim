package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class GrottoCard extends DomCard {
    private ArrayList<DomCard> myGrottodCards = new ArrayList<DomCard>();

    public GrottoCard() {
      super( DomCardName.Grotto);
    }

    public void play() {
        owner.addActions(1);
        if (owner.getCardsInHand().isEmpty())
            return;
        int havenCount = 0;
        //first look for excess terminals
        ArrayList<DomCard> theTerminalsInHand = owner.getCardsFromHand(DomCardType.Terminal);
        Collections.sort(theTerminalsInHand, DomCard.SORT_FOR_DISCARD_FROM_HAND);
        int probableActionsLeft = owner.getProbableActionsLeft();
        while (probableActionsLeft < 0 && havenCount < 4 && !theTerminalsInHand.isEmpty()) {
            grottoAway(theTerminalsInHand.remove(0));
            probableActionsLeft++;
            havenCount++;
        }
        Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
        DomCard theCardToDiscard;
        while (!owner.getCardsInHand().isEmpty() && havenCount < 4) {
            theCardToDiscard = owner.getCardsInHand().get(0);
            if (theCardToDiscard.getDiscardPriority(owner.getActionsAndVillagersLeft()) >= 15)
                break;
            grottoAway(theCardToDiscard);
            havenCount++;
        }
        boolean havenedNothing=false;
        while (havenCount < 4 && !havenedNothing) {
            havenedNothing=true;
            for (int i = 0; i<owner.getCardsInHand().size(); i++) {
                DomCard theCardToHavenAway = owner.getCardsInHand().get(i);
                if (theCardToHavenAway.hasCardType(DomCardType.Treasure)
                        && !owner.removingReducesBuyingPower(theCardToHavenAway)) {
                    grottoAway(theCardToHavenAway);
                    havenCount++;
                    havenedNothing=false;
                    break;
                }
            }
        }
        if (havenCount<4 && !owner.getCardsFromHand(DomCardName.Copper).isEmpty() && owner.wants(DomCardName.Delve) && owner.getTotalPotentialCurrency().getCoins()==3) {
            grottoAway(owner.getCardsFromHand(DomCardName.Copper).get(0));
            havenCount++;
        }
        while (havenCount<4 && !owner.getCardsFromHand(DomCardName.Grotto).isEmpty()) {
            grottoAway(owner.getCardsFromHand(DomCardName.Grotto).get(0));
            havenCount++;
        }
        //this has a bad impact on win rate
//        while (!owner.getCardsInHand().isEmpty() && havenCount<4 && owner.count(DomCardName.Province)>0 && !owner.isGoingToBuyTopCardInBuyRules(owner.getTotalPotentialCurrency())) {
//            grottoAway(owner.getCardsInHand().get(0));
//            havenCount++;
//        }
    }

    private void grottoAway(DomCard aCard) {
        if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " puts " + aCard.getName().toHTML() +" in the Grotto" );
        myGrottodCards.add(owner.removeCardFromHand(aCard));
	}

    public void resolveDuration() {
        int toDraw = myGrottodCards.size();
        if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " discards cards from " + DomCardName.Grotto.toHTML());
        owner.discard(myGrottodCards);
        myGrottodCards.clear();
        owner.drawCards(toDraw);
    }

    public void cleanVariablesFromPreviousGames() {
        myGrottodCards.clear();
    }

    @Override
    public boolean durationFailed() {
        return myGrottodCards.isEmpty();
    }
}