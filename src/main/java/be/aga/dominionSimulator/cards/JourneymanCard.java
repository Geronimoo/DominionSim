package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;

public class JourneymanCard extends DomCard {
    public JourneymanCard() {
      super( DomCardName.Journeyman);
    }

    public void play() {
      Map<DomCardName,Integer> theCardsToConsider = new EnumMap<DomCardName,Integer>(DomCardName.class);
      for (DomCard theCard : owner.getDeck().getDrawOrAndDiscardDeck()) {
        if (theCard.getDiscardPriority(owner.getActionsLeft())>DomCardName.Estate.getDiscardPriority(0))
            continue;
        if (!theCardsToConsider.containsKey(theCard.getName()))
          theCardsToConsider.put(theCard.getName(),0);
        theCardsToConsider.put(theCard.getName(),theCardsToConsider.get(theCard.getName())+1);
      }
      DomCardName theChosenCard = DomCardName.Curse;
      int theMaxAmount = 0;
      for (DomCardName theCard:theCardsToConsider.keySet()) {
         if (theCardsToConsider.get(theCard)>theMaxAmount) {
             theChosenCard=theCard;
             theMaxAmount=theCardsToConsider.get(theCard);
         }
      }
      if (theMaxAmount==owner.getDeckSize()) {
          theChosenCard = DomCardName.Curse;
      }
      if (DomEngine.haveToLog) DomEngine.addToLog( owner + " chooses " +theChosenCard.toHTML());
      ArrayList<DomCard> theCards = owner.revealUntilThreeOfCardNotNamed(theChosenCard);
      owner.getCardsInHand().addAll(theCards);
      owner.showHand();
    }
    @Override
    public int getPlayPriority() {
        return owner.getActionsLeft()>1 ? 6 : super.getPlayPriority();
    }
}