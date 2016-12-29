package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class CounterfeitCard extends DomCard {
    public CounterfeitCard() {
      super( DomCardName.Counterfeit);
    }

    public void play() {
        owner.addAvailableBuys(1);
        owner.addAvailableCoins(1);
        ArrayList<DomCard> theTreasures = owner.getCardsFromHand(DomCardType.Treasure);
        if (theTreasures.isEmpty())
            return;
        Collections.sort(theTreasures,SORT_FOR_TRASHING);
        DomCard theCardToPlayTwice;
        if (!owner.getCardsFromHand(DomCardName.Spoils).isEmpty())
            theCardToPlayTwice=owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Spoils).get(0));
        else
            if (!owner.getCardsFromHand(DomCardName.Capital).isEmpty())
                theCardToPlayTwice=owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Capital).get(0));
            else
                theCardToPlayTwice = owner.removeCardFromHand(theTreasures.get(0));
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " chooses " + theCardToPlayTwice+ " to counterfeit");
        owner.getCardsInPlay().add(theCardToPlayTwice);
        if (theCardToPlayTwice.getName()==DomCardName.Spoils) {
            theCardToPlayTwice.play();
            owner.addAvailableCoins(3);
            return;
        }
        theCardToPlayTwice.play();
        //fix for Horn of Plenty which might be trashed
        if (theCardToPlayTwice.owner==null)
            theCardToPlayTwice.owner=owner;
        theCardToPlayTwice.play();
        if (!owner.getCardsFromPlay(theCardToPlayTwice.getName()).isEmpty())
            owner.trash(owner.removeCardFromPlay(theCardToPlayTwice));
    }
}