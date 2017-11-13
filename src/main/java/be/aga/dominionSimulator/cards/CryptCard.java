package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class CryptCard extends DomCard {
    private ArrayList<DomCard> myArchivedCards = new ArrayList<DomCard>();

    public CryptCard() {
      super( DomCardName.Crypt);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      ArrayList<DomCard> theCardsToCrypt = owner.getCardsFromPlay(DomCardType.Treasure);
      if (DomEngine.haveToLog) DomEngine.addToLog(owner + " removes "+theCardsToCrypt);
      myArchivedCards.addAll(theCardsToCrypt);
      for (DomCard theCard : theCardsToCrypt)
          owner.removeCardFromPlay(theCard);
      owner.setNeedsToUpdate();
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChosenCards = new ArrayList<DomCardName>();
        owner.getEngine().getGameFrame().askToSelectCards("Remove for Crypt" , owner.getCardsFromPlay(DomCardType.Treasure), theChosenCards, 0);
        if (DomEngine.haveToLog) DomEngine.addToLog(owner + " removes "+(theChosenCards.isEmpty()?"nothing" : theChosenCards));
        for (DomCardName theCardName: theChosenCards) {
            myArchivedCards.add(owner.removeCardFromPlay(owner.getCardsFromPlay(theCardName).get(0)));
        }
    }

    private void addCardToHandFromArchive() {
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
            for (DomCard theCard : myArchivedCards) {
                theChooseFrom.add(theCard.getName());
            }
            DomCardName theChosenCard = null;
            if (theChooseFrom.size()==1) {
                theChosenCard = theChooseFrom.get(0);
            } else {
                theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Take in hand", theChooseFrom, "Mandatory!");
            }
            for (DomCard theCard : myArchivedCards) {
                if (theCard.getName()==theChosenCard) {
                    owner.addCardToHand(myArchivedCards.remove(myArchivedCards.indexOf(theCard)));
                    break;
                }
            }
        } else {
            Collections.sort(myArchivedCards,SORT_FOR_DISCARDING);
            for (DomCard theCard : myArchivedCards) {
                if (owner.addingThisIncreasesBuyingPower(theCard.getCost(owner.getCurrentGame()))){
                    owner.addCardToHand(myArchivedCards.remove(myArchivedCards.indexOf(theCard)));
                    return;
                }
            }
            owner.addCardToHand(myArchivedCards.remove(0));
        }
    }

    public void resolveDuration() {
      if (owner.isHumanOrPossessedByHuman())
          owner.setNeedsToUpdate();
      if (myArchivedCards.isEmpty())
          return;
      addCardToHandFromArchive();
    }

    @Override
    public boolean mustStayInPlay() {
        return !myArchivedCards.isEmpty();
    }

    @Override
    public void cleanVariablesFromPreviousGames() {
        myArchivedCards.clear();
    }
}