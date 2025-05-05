package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.Collections;

public class Cabin_BoyCard extends DomCard {
    public Cabin_BoyCard() {
      super( DomCardName.Cabin_Boy);
    }

    public void play() {
        owner.addActions(1);
        owner.drawCards(1);
    }

    @Override
    public void resolveDuration() {
        DomCardName theDesiredCard = owner.getDesiredCard(DomCardType.Duration, new DomCost(1000, 0), false, true, null);
        if (theDesiredCard!=null
                && owner.stillInEarlyGame()
                && theDesiredCard!=DomCardName.Cabin_Boy
                && owner.getCurrentGame().countInSupply(theDesiredCard)>0 ) {
            DomPlayer theOwner = owner;
            theOwner.trash(theOwner.removeCardFromPlay(this));
            theOwner.gain(theDesiredCard);
        } else {
            owner.addAvailableCoins(2);
        }
    }
}