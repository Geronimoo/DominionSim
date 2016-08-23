package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class SpyCard extends DomCard {
    public SpyCard () {
      super( DomCardName.Spy);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      spyOnYourself();
      spyOnOpponents();
    }

	private void spyOnOpponents() {
		DomCard theRevealedCard = null;
		for (DomPlayer thePlayer : owner.getOpponents()) {
	      if (thePlayer.checkDefense()) 
	    	continue;
	      ArrayList<DomCard> theRevealedCards = thePlayer.revealTopCards(1);
	      if (theRevealedCards.isEmpty()) 
	    	continue;
	      theRevealedCard=theRevealedCards.get(0);
    	  if (theRevealedCard.getDiscardPriority(1)<16
    	  || (!owner.getCardsFromHand(DomCardName.Noble_Brigand).isEmpty() && findMoneyForNoble_Brigand(theRevealedCard)) 
      	  || (!owner.getCardsFromHand(DomCardName.Thief).isEmpty() && findMoneyForThief(theRevealedCard))) {
            thePlayer.putOnTopOfDeck(theRevealedCard);    		
    	  } else {
      		thePlayer.discard(theRevealedCard);
    	  }
	    }
	}

	private boolean findMoneyForNoble_Brigand(DomCard theRevealedCard) {
		if (theRevealedCard.getName()==DomCardName.Silver || theRevealedCard.getName()==DomCardName.Gold)
			return true;
		return false;
	}

	private boolean findMoneyForThief(DomCard theRevealedCard) {
		if (theRevealedCard.hasCardType(DomCardType.Treasure)
				&& theRevealedCard.getName().getTrashPriority(owner)>=20)
			return true;
		return false;
	}

	private void spyOnYourself() {
		ArrayList<DomCard> theRevealedCard = owner.revealTopCards(1);
		  if (theRevealedCard.isEmpty()) 
			  return;
		  if (theRevealedCard.get(0).getDiscardPriority(1)<16) {
			owner.discard(theRevealedCard);
		  } else {
	        owner.putOnTopOfDeck(theRevealedCard.get(0));    		
		  }
	}
}