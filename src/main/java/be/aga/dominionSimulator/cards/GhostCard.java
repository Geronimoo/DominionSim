package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class GhostCard extends DomCard {
    private DomCard myDurationCard = null;

    public GhostCard() {
      super( DomCardName.Ghost);
    }

    @Override
    public void play() {
        ArrayList<DomCard> theRevealedCards = owner.revealUntilType(DomCardType.Action);
        if (theRevealedCards.isEmpty()) {
            return;
        }
        if (!theRevealedCards.get(theRevealedCards.size()-1).hasCardType(DomCardType.Action)) {
            owner.discard(theRevealedCards);
            return;
        }
        myDurationCard =theRevealedCards.remove(theRevealedCards.size()-1);
        owner.discard(theRevealedCards);
    }

    @Override
    public void resolveDuration() {
        if (owner.getCardsInPlay().contains(myDurationCard)) {
            myDurationCard.resolveDuration();
        } else {
            owner.getCardsInPlay().add(myDurationCard);
            if (DomEngine.haveToLog ) {
                DomEngine.addToLog( owner + " plays " + myDurationCard );
                DomEngine.logIndentation++;
            }
            myDurationCard.owner=owner;
            owner.playThis(myDurationCard);
            if (DomEngine.haveToLog ) {
                DomEngine.logIndentation--;
            }
            if (DomEngine.haveToLog ) {
                DomEngine.addToLog( owner + " plays " + myDurationCard +" a second time");
                DomEngine.logIndentation++;
            }
            myDurationCard.owner=owner;
            owner.playThis(myDurationCard);
            if (DomEngine.haveToLog ) {
                DomEngine.logIndentation--;
            }
            if (!myDurationCard.hasCardType(DomCardType.Duration)) {
              myDurationCard = null;
            }
        }
    }

    @Override
    public void cleanVariablesFromPreviousGames() {
        myDurationCard = null;
    }

    @Override
    public boolean mustStayInPlay() {
        return myDurationCard != null;
    }
}