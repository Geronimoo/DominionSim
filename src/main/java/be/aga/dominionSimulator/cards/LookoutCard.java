package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class LookoutCard extends DomCard {
    public LookoutCard () {
      super( DomCardName.Lookout);
    }

    public void play() {
      owner.addActions( 1 );
      ArrayList< DomCard > theRevealedCards = owner.revealTopCards( 3 );
      if (theRevealedCards.isEmpty())
        return;
      //first trash a card
      Collections.sort( theRevealedCards, SORT_FOR_TRASHING );
      owner.trash(theRevealedCards.remove( 0 ));
      if (theRevealedCards.isEmpty())
        return;
      //then discard a card
      Collections.sort( theRevealedCards, SORT_FOR_DISCARDING );
      owner.discard(theRevealedCards.remove( 0 ));
      //and finally, put the last card back on the deck
      if (!theRevealedCards.isEmpty()) {
        owner.putOnTopOfDeck(theRevealedCards.get( 0 ));
      }
    }
    @Override
    public boolean wantsToBePlayed() {
    	int theCount=0;
    	for (DomCardName card : owner.getDeck().keySet()){
    		if (card.getTrashPriority(owner)<16)
    			theCount+=owner.countInDeck(card);
    	}
    	return theCount>=4;
    }
}