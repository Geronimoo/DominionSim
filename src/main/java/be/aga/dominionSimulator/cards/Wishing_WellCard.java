package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class Wishing_WellCard extends DomCard {
    public Wishing_WellCard () {
      super( DomCardName.Wishing_Well);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      if (owner.getDeckSize()==0)
    	return;
      DomCardName theChoice = null;
      if (owner.isHumanOrPossessedByHuman()) {
          owner.setNeedsToUpdateGUI();
          ArrayList<DomCardName> theDeckCards = new ArrayList<DomCardName>();
          for (DomCardName theCard : owner.getDeck().keySet()) {
              theDeckCards.add(theCard);
          }
          Collections.sort(theDeckCards);
          theChoice = owner.getEngine().getGameFrame().askToSelectOneCard("Wish for ", theDeckCards, "Wish for Ace of Spades");
      } else {
          if (owner.getPlayStrategyFor(this) == DomPlayStrategy.goodDeckTracker)
              theChoice = owner.getDeck().getMostLikelyCardOnTop();
          else if (owner.getPlayStrategyFor(this) == DomPlayStrategy.greedyDeckTracker)
              theChoice = owner.getDeck().getMostWantedCardOnTop();
          else
              theChoice = DomCardName.Wishing_Well;
      }
      if (DomEngine.haveToLog) DomEngine.addToLog(owner + " names " + (theChoice==null?"Ace of Spades":theChoice.toHTML()));
      DomCard theRevealedCard = owner.revealTopCards(1).get(0);
	  if (theRevealedCard.getName()==theChoice){
        owner.putInHand(theRevealedCard);
	  }else{
        owner.putOnTopOfDeck(theRevealedCard);
      }
    }

    @Override
    public int getPlayPriority() {
        if (owner.getKnownTopCards()>=2)
            return 0;
        return super.getPlayPriority();
    }
}