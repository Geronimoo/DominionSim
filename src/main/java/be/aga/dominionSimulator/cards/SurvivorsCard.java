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
      layOutMaps();
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