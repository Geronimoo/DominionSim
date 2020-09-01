package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.*;

public class JourneymanCard extends DomCard {
    public JourneymanCard() {
      super( DomCardName.Journeyman);
    }

    public void play() {
      DomCardName theChosenCard = null;
      if (owner.isHumanOrPossessedByHuman()) {
          theChosenCard=handleHuman();
      } else {
          Map<DomCardName, Integer> theCardsToConsider = new EnumMap<DomCardName, Integer>(DomCardName.class);
          for (DomCard theCard : owner.getDeck().getDrawOrAndDiscardDeck()) {
              if (theCard.getDiscardPriority(owner.getActionsAndVillagersLeft()) > DomCardName.Estate.getDiscardPriority(0))
                  continue;
              if (!theCardsToConsider.containsKey(theCard.getName()))
                  theCardsToConsider.put(theCard.getName(), 0);
              theCardsToConsider.put(theCard.getName(), theCardsToConsider.get(theCard.getName()) + 1);
          }
          theChosenCard= DomCardName.Curse;
          int theMaxAmount = 0;
          for (DomCardName theCard : theCardsToConsider.keySet()) {
              if (theCardsToConsider.get(theCard) > theMaxAmount) {
                  theChosenCard = theCard;
                  theMaxAmount = theCardsToConsider.get(theCard);
              }
          }
          if (theMaxAmount == owner.getDeckAndDiscardSize()) {
              theChosenCard = DomCardName.Curse;
          }
      }
      if (DomEngine.haveToLog) DomEngine.addToLog( owner + " chooses " +theChosenCard.toHTML());
      ArrayList<DomCard> theCards = owner.revealUntilThreeOfCardNotNamed(theChosenCard);
      owner.getCardsInHand().addAll(theCards);
      owner.showHand();
    }

    private DomCardName handleHuman() {
        ArrayList<DomCardName> theDeckCards = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getDeck().keySet()) {
            theDeckCards.add(theCard);
        }
        Collections.sort(theDeckCards);
        return owner.getEngine().getGameFrame().askToSelectOneCard("Name a card", theDeckCards, "Name Ace of Spades");
    }

    @Override
    public int getPlayPriority() {
        return owner.getActionsAndVillagersLeft()>1 ? 6 : super.getPlayPriority();
    }
}