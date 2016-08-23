package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class StablesCard extends DomCard {
    public StablesCard () {
      super( DomCardName.Stables);
    }

    public void play() {
      if (!discardTreasureFromHand())
    	  return;
      owner.addActions(1);
      owner.drawCards(3);
    }
    
    private boolean discardTreasureFromHand() {
    	if (owner.getCardsFromHand(DomCardType.Treasure).isEmpty())
    		return false;
    	ArrayList<DomCard> theCards = owner.getCardsFromHand(DomCardType.Treasure);
		Collections.sort(theCards, SORT_FOR_DISCARDING);
	    owner.discardFromHand(theCards.get(0));
		return true;
	}

	@Override
    public int getPlayPriority() {
    	if (owner.getCardsFromHand(DomCardType.Treasure).isEmpty())
    		return 1000;
    	return super.getPlayPriority();
    }
}