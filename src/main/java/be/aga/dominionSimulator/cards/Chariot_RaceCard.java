package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class Chariot_RaceCard extends DomCard {
    public Chariot_RaceCard() {
        super(DomCardName.Chariot_Race);
    }

    public void play() {
        owner.addActions(1);
        if (owner.getDeckSize()==0 || owner.getOpponents().isEmpty())
            return;
        DomPlayer theLeftOpp = owner.getOpponents().get(0);
        if (theLeftOpp.getDeckSize()==0)
            return;
        DomCard theRevealedCard = owner.revealTopCards(1).get(0);
        DomCard theOppRevealedCard = theLeftOpp.revealTopCards(1).get(0);

        if (theRevealedCard.getCost(owner.getCurrentGame()).compareTo(theOppRevealedCard.getCost(owner.getCurrentGame()))>0) {
            owner.addAvailableCoins(1);
            owner.addVP(1);
        }
        owner.putInHand(theRevealedCard);
        theLeftOpp.putOnTopOfDeck(theOppRevealedCard);
    }
}