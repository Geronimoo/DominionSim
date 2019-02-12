package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class CartographerCard extends DomCard {
    public CartographerCard () {
      super( DomCardName.Cartographer);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      layOutMaps();
    }

	private void layOutMaps() {
	  ArrayList<DomCard> theLookedAtCards = owner.getTopCards(4);
	  if (theLookedAtCards.isEmpty())
	    return;
	  if (owner.isHumanOrPossessedByHuman()) {
	  	handleHuman(theLookedAtCards);
	  	return;
	  }
	  Collections.sort(theLookedAtCards,SORT_FOR_DISCARDING);
	  for (DomCard theRevealedCard : theLookedAtCards){
		  if (theRevealedCard.getDiscardPriority(1)<16) {
			owner.discard(theRevealedCard);
		  } else {
	        owner.putOnTopOfDeck(theRevealedCard);    		
		  }
	  }
	}

	private void handleHuman(ArrayList<DomCard> theRevealedCards) {
		owner.setNeedsToUpdateGUI();
		ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
		owner.getEngine().getGameFrame().askToSelectCards("<html>Discard ?</html>", theRevealedCards, theChosenCards, 0);
		for (int i = theChosenCards.size() - 1; i >= 0; i--) {
			for (DomCard theCard : theRevealedCards) {
				if (theChosenCards.get(i).getName() == theCard.getName()) {
					owner.discard(theCard);
					theRevealedCards.remove(theCard);
					break;
				}
			}
		}
		if (theRevealedCards.isEmpty())
			return;
		theChosenCards = new ArrayList<DomCard>();
		owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>", theRevealedCards, theChosenCards, 0);
		if (theChosenCards.size() < theRevealedCards.size()) {
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

	@Override
	public int getPlayPriority() {
		if (owner.getKnownTopCards()==0)
			return 3;
		return super.getPlayPriority();
	}
}