package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Set;

public class ClerkCard extends DomCard {
    public ClerkCard() {
      super( DomCardName.Clerk);
    }

    public void play() {
        owner.addAvailableCoins(2);
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (!thePlayer.checkDefense() && thePlayer.getCardsInHand().size()>4) {
                thePlayer.putCardFromHandOnTop();
            }
        }
    }
}