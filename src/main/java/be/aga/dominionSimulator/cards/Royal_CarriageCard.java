package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Royal_CarriageCard extends DomCard {
    private DomCard myDurationCard;

    public Royal_CarriageCard() {
      super( DomCardName.Royal_Carriage);
    }

    public void play() {
      owner.addActions(1);
      if (owner.getCardsInPlay().contains(this))
        owner.putOnTavernMat(owner.removeCardFromPlay(this));
    }

    @Override
    public void doWhenCalled() {
        cleanVariablesFromPreviousGames();
        int i=0;
        for (i=owner.getCardsInPlay().size()-1;i>=0;i--) {
            if (owner.getCardsInPlay().get(i).getName()==DomCardName.Royal_Carriage)
                continue;
            break;
        }
        DomCard theCardToPlay = owner.removeCardFromPlay(owner.getCardsInPlay().get(i));
        owner.play(theCardToPlay);
        if (theCardToPlay.hasCardType(DomCardType.Duration)) {
            myDurationCard = theCardToPlay;
            setDiscardAtCleanup(false);
        }
    }

    @Override
    public void cleanVariablesFromPreviousGames() {
        myDurationCard = null;
    }

    public void resolveDuration() {
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
}