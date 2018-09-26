package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class ApothecaryCard extends DomCard {
    public ApothecaryCard () {
      super( DomCardName.Apothecary);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      ArrayList<DomCard> theRevealedCards = owner.revealTopCards( 4 );
      if (theRevealedCards.isEmpty())
    	  return;
      DomCard theCardToGrab=null;
      do {
    	theCardToGrab=null;
    	for (DomCard theCard : theRevealedCards) {
    	  if (theCard.getName()==DomCardName.Copper
    	   || theCard.getName()==DomCardName.Potion) {
    	    theCardToGrab=theCard;
    		break;
          }
    	}
    	if (theCardToGrab!=null)
    	  owner.putInHand(theRevealedCards.remove(theRevealedCards.indexOf(theCardToGrab)));
      } while (theCardToGrab!=null && !theRevealedCards.isEmpty());
      Collections.sort(theRevealedCards, SORT_FOR_DISCARDING);
      if (!theRevealedCards.isEmpty() 
        && owner.getPlayStrategyFor(this)==DomPlayStrategy.ApothecaryNativeVillage)
    	checkForTheCombo(theRevealedCards);
      if (owner.isHumanOrPossessedByHuman()) {
      	handleHuman(theRevealedCards);
	  } else {
		  for (DomCard theCard : theRevealedCards) {
			  owner.putOnTopOfDeck(theCard);
		  }
	  }
    }

	private void handleHuman(ArrayList<DomCard> theRevealedCards) {
    	if (theRevealedCards.isEmpty())
    		return;
    	owner.setNeedsToUpdateGUI();
		ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
		owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>" , theRevealedCards, theChosenCards, 0);
		if (theChosenCards.size()<theRevealedCards.size()) {
			for (DomCard theCard : theRevealedCards) {
				owner.putOnTopOfDeck(theCard);
			}
		} else {
			for (int i = theChosenCards.size() - 1; i >= 0; i--) {
				for (DomCard theCard : theRevealedCards) {
					if (theChosenCards.get(i).getName() == theCard.getName()) {
						owner.putOnTopOfDeck(theCard);
						theRevealedCards.remove(theCard);
						break;
					}
				}
			}
		}
	}

	private void checkForTheCombo(ArrayList<DomCard> theRevealedCards) {
  	  if (owner.getCardsFromHand(DomCardName.Native_Village).isEmpty())
  		  return;
  	  if (theRevealedCards.get(0).getDiscardPriority(1)>=DomCardName.Copper.getDiscardPriority(1))
  		  return;
  	  theRevealedCards.add(theRevealedCards.remove(0));
	}
	@Override
	public int getPlayPriority() {
		if (owner.getKnownTopCards()==0)
			return 3;
		return super.getPlayPriority();
	}
}