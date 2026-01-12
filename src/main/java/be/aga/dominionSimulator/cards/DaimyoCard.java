package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class DaimyoCard extends DomCard {
    private DomCard myDurationCard;

    public DaimyoCard() {
      super( DomCardName.Daimyo);
    }

    public void play() {
        owner.drawCards(1);
        owner.addActions(1);
        owner.addDaimyoTrigger(this);
    }

    @Override
    public void cleanVariablesFromPreviousGames() {
        myDurationCard = null;
    }

    public void resolveDuration() {
        if (myDurationCard==null)
            return;
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " played " +myDurationCard+ " with "+ this);
        myDurationCard.resolveDuration();
        if (!myDurationCard.mustStayInPlay())
            myDurationCard=null;
    }

    @Override
    public boolean mustStayInPlay() {
        if (myDurationCard==null)
            return false;
        return myDurationCard.mustStayInPlay();
    }

    @Override
    public boolean durationFailed() {
        return myDurationCard==null;
    }

    public void setDuration(DomCard aCard) {
        myDurationCard=aCard;
    }
}