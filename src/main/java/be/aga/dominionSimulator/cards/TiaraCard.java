package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class TiaraCard extends DomCard {
    public TiaraCard() {
      super( DomCardName.Tiara);
    }

    public void play() {
        owner.addAvailableBuys(1);
        ArrayList<DomCard> theTreasures = owner.getCardsFromHand(DomCardType.Treasure);
        if (theTreasures.isEmpty())
            return;
        Collections.sort(theTreasures,SORT_FOR_TRASHING);
        DomCard theCardToPlayTwice = theTreasures.get(theTreasures.size()-1);
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " chooses " + theCardToPlayTwice+ " to Tiara");
        owner.getCardsInPlay().add(owner.removeCardFromHand(theCardToPlayTwice));
        if (theCardToPlayTwice.getName()==DomCardName.Spoils) {
            theCardToPlayTwice.play();
            owner.addAvailableCoins(3);
            return;
        }
        theCardToPlayTwice.play();
        if (theCardToPlayTwice.owner!=null)
          theCardToPlayTwice.play();
    }
}