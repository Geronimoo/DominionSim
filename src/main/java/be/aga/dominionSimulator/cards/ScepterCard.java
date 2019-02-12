package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class ScepterCard extends DomCard {
    private DomCard myDurationCard;

    public ScepterCard() {
      super( DomCardName.Scepter);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
        } else {
            int i = 0;
            boolean actionFound = false;
            Collections.sort(owner.getCardsInPlay(), SORT_FOR_DISCARDING);
            for (i = owner.getCardsInPlay().size() - 1; i >= 0; i--) {
                if (!owner.getCardsInPlay().get(i).hasCardType(DomCardType.Action)
                        || owner.getCardsInPlay().get(i).getName() == DomCardName.Royal_Carriage
                        || (owner.getCardsInPlay().get(i).hasCardType(DomCardType.Duration) && owner.getCardsInPlay().get(i).getDiscardAtCleanUp())
                        || (owner.getCardsInPlay().get(i).getCoinValue() < 2 && !owner.getCardsInPlay().get(i).hasCardType(DomCardType.Card_Advantage)))
                    continue;
                actionFound = true;
                break;
            }
            if (!actionFound) {
                owner.addAvailableCoins(2);
            } else {
                DomCard theCardToPlay = owner.removeCardFromPlay(owner.getCardsInPlay().get(i));
                owner.play(theCardToPlay);
                if (theCardToPlay.hasCardType(DomCardType.Duration)) {
                    myDurationCard = theCardToPlay;
                    setDiscardAtCleanup(false);
                }
            }
        }
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsFromPlay(DomCardType.Action)) {
            theChooseFrom.add(theCard.getName());
        }
        if (theChooseFrom.isEmpty()) {
            owner.addAvailableCoins(2);
            return;
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Replay a card?", theChooseFrom, "Don't replay an action, but add $2");
        if (theChosenCard!=null) {
            DomCard theCardToPlay = owner.removeCardFromPlay(owner.getCardsFromPlay(theChosenCard).get(0));
            owner.play(theCardToPlay);
            if (theCardToPlay.hasCardType(DomCardType.Duration)) {
                myDurationCard = theCardToPlay;
                setDiscardAtCleanup(false);
            }
        } else {
            owner.addAvailableCoins(2);
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