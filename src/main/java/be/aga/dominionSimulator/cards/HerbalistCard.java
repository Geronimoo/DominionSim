package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class HerbalistCard extends DomCard {
    public HerbalistCard () {
      super( DomCardName.Herbalist);
    }

    public void play() {
      owner.addAvailableCoins(1);
      owner.addAvailableBuys(1);
    }
    
	public void maybeAddTagFor(ArrayList<DomCard> theCardsToHandle) {
      for (int i=theCardsToHandle.size()-1;i>=0;i--) {
    	DomCard theCard = theCardsToHandle.get(i);
    	if (!theCard.isTaggedByHerbalist()
    	 && theCard.getDiscardPriority(1)>20 
    	 && theCard.hasCardType(DomCardType.Treasure) ) {
    		theCard.addHerbalistTag();
    		break;
    	}
      }
	}
}