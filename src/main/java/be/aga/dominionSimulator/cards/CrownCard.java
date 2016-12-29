package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;

import java.util.ArrayList;
import java.util.Collections;

public class CrownCard extends MultiplicationCard {
    public CrownCard() {
      super( DomCardName.Crown);
    }

    @Override
    public void play() {
        if (owner.getPhase()== DomPhase.Action) {
            super.play();
            return;
        }
        ArrayList<DomCard> theTreasures = owner.getCardsFromHand(DomCardType.Treasure);
        if (theTreasures.isEmpty())
            return;
        Collections.sort(theTreasures, SORT_FOR_DISCARDING);
        DomCard theCardToPlayTwice;
        if (!owner.getCardsFromHand(DomCardName.Spoils).isEmpty())
            theCardToPlayTwice=owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Spoils).get(0));
        else
            if (!owner.getCardsFromHand(DomCardName.Capital).isEmpty())
                theCardToPlayTwice=owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Capital).get(0));
            else
                theCardToPlayTwice = owner.removeCardFromHand(theTreasures.get(theTreasures.size()-1));
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " chooses " + theCardToPlayTwice + " to Crown");
        owner.getCardsInPlay().add(theCardToPlayTwice);
        if (theCardToPlayTwice.getName()==DomCardName.Spoils) {
            theCardToPlayTwice.play();
            owner.addAvailableCoins(3);
            return;
        }
        theCardToPlayTwice.play();
        //fix for Horn of Plenty which might be trashed
        if (theCardToPlayTwice.owner==null) {
          theCardToPlayTwice.owner = owner;
          theCardToPlayTwice.play();
          theCardToPlayTwice.owner = null;
        }else {
          theCardToPlayTwice.play();
        }

    }

    @Override
    public int getPlayPriority() {
        if (owner.getPhase()==DomPhase.Buy)
            return 1;
        return super.getPlayPriority();
    }

    @Override
    public boolean wantsToBePlayed() {
        if (owner.getPhase()==DomPhase.Action && (owner.getCardsFromHand(DomCardType.Action).size()<2 || !owner.getCardsFromHand(DomCardName.Capital).isEmpty()))
            return false;
        return true;
    }
}