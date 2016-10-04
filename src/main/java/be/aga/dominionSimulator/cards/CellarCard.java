package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class CellarCard extends DomCard {
    private int discardCount;
	private int deckSize;
    private double drawDeckSize;
    private int badReshuffleTreshold;

    public CellarCard () {
      super( DomCardName.Cellar);
    }

    public void play() {
      checkBadReshuffle();
  	  deckSize = owner.getDeckSize();
      drawDeckSize = owner.getDrawDeckSize();
      owner.addActions( 1 );
      discardCount=0;
      discardExcessTerminalActions();
      discardOtherCellars();
      discardBadCards();
      owner.drawCards( discardCount );
    }

    private void checkBadReshuffle() {
        if (owner.getCardsFromDiscard().size()==0) {
            badReshuffleTreshold=10000000;
            return;
        }
        int theTotalDiscard = 0;
        for (DomCard theCard : owner.getCardsFromDiscard()) {
            theTotalDiscard+=theCard.getDiscardPriority(1);
        }
        if (theTotalDiscard/owner.getCardsFromDiscard().size()<=DomCardName.Copper.getDiscardPriority(1))
            badReshuffleTreshold= (int) drawDeckSize;
        else
            badReshuffleTreshold=10000000;
    }

    private void discardOtherCellars() {
	  ArrayList< DomCard > theCellarsInHand = owner.getCardsFromHand( DomCardName.Cellar);
	  for (int i = 0;i<theCellarsInHand.size() && discardCount<deckSize;i++) {
		owner.discardFromHand(theCellarsInHand.get(i));
		discardCount++;
	  }
	}

	private void discardBadCards() {
 	  Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
	  while (!owner.getCardsInHand().isEmpty() && discardCount<deckSize ) {
          //does not work well so removed for now
//              && discardCount<badReshuffleTreshold) {
		DomCard theCardToDiscard = owner.getCardsInHand().get(0);
		if (theCardToDiscard.getDiscardPriority(1)<16) {
	    	owner.discardFromHand(theCardToDiscard);
	    	discardCount++;
		} else {
			break;
		}
	  }
	}

	private void discardExcessTerminalActions() {
	  ArrayList< DomCard > theTerminalsInHand = owner.getCardsFromHand( DomCardType.Terminal );
	  Collections.sort(theTerminalsInHand, DomCard.SORT_FOR_DISCARD_FROM_HAND);
	  int theNumber=theTerminalsInHand.size()-owner.getActionsLeft();
	  for (int i = 0;i<theNumber && discardCount<deckSize;i++) {
		owner.discardFromHand(theTerminalsInHand.get(i));
		discardCount++;
	  }
	}
}