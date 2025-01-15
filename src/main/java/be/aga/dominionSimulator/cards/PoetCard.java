package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class PoetCard extends DomCard {
    public PoetCard() {
      super( DomCardName.Poet);
    }

    public void play() {
        owner.addSunForProphecy(1);
        owner.addActions(1);
        owner.drawCards(1);
        ArrayList<DomCard> revealedCard = owner.revealTopCards(1);
        if (revealedCard.isEmpty())
            return;
        if (revealedCard.get(0).getCoinCost(owner.getCurrentGame())<=3 && revealedCard.get(0).getDebtCost()==0 && revealedCard.get(0).getPotionCost()==0) {
            owner.addCardToHand(revealedCard.get(0));
        } else {
            owner.putOnTopOfDeck(revealedCard.get(0));
        }
    }
}