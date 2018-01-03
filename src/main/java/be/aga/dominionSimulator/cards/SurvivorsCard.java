package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class SurvivorsCard extends DomCard {
    public SurvivorsCard () {
      super( DomCardName.Survivors);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman())
      	handleHuman();
      else
      	layOutMaps();
    }

	private void handleHuman() {
		ArrayList<DomCard> theTopCards = owner.revealTopCards(2);
		if (theTopCards.isEmpty())
			return;
		if (owner.getEngine().getGameFrame().askPlayer("<html>Discard " + theTopCards +" ?</html>", "Resolving " + this.getName().toString())) {
			owner.discard(theTopCards);
		} else {
			ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
			do {
				theChosenCards = new ArrayList<DomCard>();
				owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>", theTopCards, theChosenCards, 0);
			} while (theChosenCards.size()==1);
			if (theChosenCards.size()<2) {
				owner.putOnTopOfDeck(theTopCards.get(1));
				owner.putOnTopOfDeck(theTopCards.get(0));
			} else {
				if (theTopCards.get(1).getName() == theChosenCards.get(1).getName()) {
					owner.putOnTopOfDeck(theTopCards.get(1));
					owner.putOnTopOfDeck(theTopCards.get(0));
				} else {
					owner.putOnTopOfDeck(theTopCards.get(0));
					owner.putOnTopOfDeck(theTopCards.get(1));
				}
			}
		}
	}

	private void layOutMaps() {
	  int theTotal=0;
	  ArrayList<DomCard> theCards = owner.revealTopCards(2);
	  if (theCards.isEmpty()) 
	    return;
      for (DomCard card : theCards){
    	theTotal+=card.getDiscardPriority(1);
    	if (card.getName()==DomCardName.Tunnel){
    		owner.discard(theCards);
    		return;
    	}
      }
      if (theTotal<32) {
        owner.discard(theCards);
      } else {
    	  Collections.sort(theCards,SORT_FOR_DISCARD_FROM_HAND);
    	  for (DomCard theCard : theCards) {
    	    owner.putOnTopOfDeck(theCard);
    	  }
      }
	}
}