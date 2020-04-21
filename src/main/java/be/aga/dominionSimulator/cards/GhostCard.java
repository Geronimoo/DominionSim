package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class GhostCard extends DomCard {
    private DomCard myCardToGhost = null;

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
        myCardToGhost =theRevealedCards.remove(theRevealedCards.size()-1);
        owner.discard(theRevealedCards);
    }

    @Override
    public void resolveDuration() {
        if (owner.getCardsInPlay().contains(myCardToGhost)) {
            myCardToGhost.resolveDuration();
        } else {
            owner.getCardsInPlay().add(myCardToGhost);
            if (DomEngine.haveToLog ) {
                DomEngine.addToLog( owner + " plays " + myCardToGhost);
                DomEngine.logIndentation++;
            }
            myCardToGhost.owner=owner;
            owner.playThis(myCardToGhost);
            if (DomEngine.haveToLog ) {
                DomEngine.logIndentation--;
            }
            if (DomEngine.haveToLog ) {
                DomEngine.addToLog( owner + " plays " + myCardToGhost +" a second time");
                DomEngine.logIndentation++;
            }
            myCardToGhost.owner=owner;
            owner.playThis(myCardToGhost);
            if (DomEngine.haveToLog ) {
                DomEngine.logIndentation--;
            }
            if (!myCardToGhost.hasCardType(DomCardType.Duration)) {
              myCardToGhost = null;
            }
        }
    }

    @Override
    public void cleanVariablesFromPreviousGames() {
        myCardToGhost = null;
    }

    @Override
    public boolean mustStayInPlay() {
        return myCardToGhost != null;
    }
}