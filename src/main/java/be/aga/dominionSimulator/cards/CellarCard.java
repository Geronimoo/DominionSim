package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class CellarCard extends DomCard {
    private int discardCount;
	private int deckSize;

	public CellarCard () {
      super( DomCardName.Cellar);
    }

    public void play() {
  	  deckSize = owner.getDeckSize();
      owner.addActions( 1 );
      discardCount=0;
      discardExcessTerminalActions();
      discardOtherCellars();
      discardBadCards();
      owner.drawCards( discardCount );
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
	  while (!owner.getCardsInHand().isEmpty() && discardCount<deckSize) {
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