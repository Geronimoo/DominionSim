package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomGame;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class Mountain_PassCard extends DomCard {
    public Mountain_PassCard() {
        super(DomCardName.Mountain_Pass);
    }

    public static void doTheAuction(DomPlayer owner) {
        if (DomEngine.haveToLog) DomEngine.addToLog("Starting auction for " + DomCardName.Mountain_Pass.toHTML());
        int highestBid = 0;
        DomPlayer highestBidder = null;
        for (DomPlayer thePlayer : owner.getOpponents()) {
            int theBid = thePlayer.getMountainPassBid();
            if (theBid > highestBid) {
                highestBid = theBid;
                highestBidder = thePlayer;
                if (DomEngine.haveToLog) DomEngine.addToLog(thePlayer + " bids highest: $" + theBid);
            } else {
                if (DomEngine.haveToLog) DomEngine.addToLog(thePlayer + " bids too low: $" + theBid);
            }
        }
        int theBid = owner.getMountainPassBid();
        if (theBid > highestBid) {
            highestBid = theBid;
            highestBidder = owner;
            if (DomEngine.haveToLog) DomEngine.addToLog(owner + " bids highest: $" + theBid);
        } else {
            if (DomEngine.haveToLog) DomEngine.addToLog(owner + " bids too low: $" + theBid);
        }
        if (highestBidder == null) {
            if (DomEngine.haveToLog) DomEngine.addToLog("Everyone passes");
            return;
        }
        highestBidder.addDebt(highestBid);
        highestBidder.addVP(8);
    }
}