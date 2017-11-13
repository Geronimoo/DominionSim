package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Trading_PostCard extends DomCard {
    public Trading_PostCard () {
      super( DomCardName.Trading_Post);
    }

    public void play() {
    	if (owner.isHumanOrPossessedByHuman()) {
    		handleHuman();
    		return;
		}
        ArrayList<DomCard> cardsInHand = owner.getCardsInHand();
        Collections.sort(cardsInHand,SORT_FOR_TRASHING);
        int i=0;
        for (;i<2 && !cardsInHand.isEmpty();i++){
          owner.trash(owner.removeCardFromHand( cardsInHand.get(0)));
        }
        if (i==2)
          owner.gainInHand(DomCardName.Silver);
    }

	private void handleHuman() {
		if (owner.getCardsInHand().size()>2) {
			ArrayList<DomCardName> theChosenCards = new ArrayList<DomCardName>();
			owner.getEngine().getGameFrame().askToSelectCards("Choose 2 cards to trash", owner.getCardsInHand(), theChosenCards, 2);
			for (DomCardName theCard:theChosenCards) {
				owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theCard).get(0)));
			}
			owner.gainInHand(DomCardName.Silver);
		} else {
			int theSize = owner.getCardsInHand().size();
			while (!owner.getCardsInHand().isEmpty())
				owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
			if (theSize==2)
				owner.gainInHand(DomCardName.Silver);
		}

	}

	@Override
    public boolean wantsToBePlayed() {
    	int theCount = 0;
    	int theSilverCount=0;
    	DomCard theBadCard=null;
    	for (DomCard card : owner.getCardsInHand()) {
    	  if (card==this)
    		  continue;
    	  if (card.getTrashPriority()<20){
    		  theCount++;
    		  theBadCard=card;
    	  }
    	  theSilverCount+= card.getName()==DomCardName.Silver ? 1 : 0;
    	}
    	if (theSilverCount>0 && theCount==1) {
          	if (owner.removingReducesBuyingPower(theBadCard))
          	  return false;
          	else 
        	  return true;
    	}
    	return theCount>=2;
    }
}