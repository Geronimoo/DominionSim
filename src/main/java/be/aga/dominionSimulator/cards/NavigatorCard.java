package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class NavigatorCard extends DomCard {
    public NavigatorCard () {
      super( DomCardName.Navigator);
    }

    public void play() {
      owner.addAvailableCoins(2);
      ArrayList<DomCard> theCards = owner.revealTopCards(5);
      int theTotal=0;
      for (DomCard card : theCards){
    	theTotal+=card.getDiscardPriority(1);
    	if (card.getName()==DomCardName.Tunnel){
    		owner.discard(theCards);
    		return;
    	}
      }
      if (theTotal<80) {
        owner.discard(theCards);
      } else {
    	  Collections.sort(theCards,SORT_FOR_DISCARD_FROM_HAND);
    	  for (DomCard theCard : theCards) {
    	    owner.putOnTopOfDeck(theCard);
    	  }
      }
    }
}