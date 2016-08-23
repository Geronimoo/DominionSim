package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class ArchiveCard extends DomCard {
    private ArrayList<DomCard> myArchivedCards = new ArrayList<DomCard>();

    public ArchiveCard() {
      super( DomCardName.Archive);
    }

    public void play() {
      owner.addActions(1);
      myArchivedCards.addAll(owner.revealTopCards(3));
      if (myArchivedCards.isEmpty())
          return;
      Collections.sort(myArchivedCards,SORT_FOR_DISCARDING);
      owner.addCardToHand(myArchivedCards.remove(myArchivedCards.size()-1));
    }

    public void resolveDuration() {
      if (myArchivedCards.isEmpty())
          return;
      owner.addCardToHand(myArchivedCards.remove(myArchivedCards.size()-1));
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