package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class TributeCard extends DomCard {
    public TributeCard () {
      super( DomCardName.Tribute);
    }

    public void play() {
      if (owner.getOpponents().isEmpty())
    	return;
      DomPlayer theLeftPlayer = owner.getOpponents().get(0);
      ArrayList<DomCard> theRevealedCards = theLeftPlayer.revealTopCards(2);
      if (theRevealedCards.isEmpty())
    	return;
      DomCardName thePrevious=null;
      for (int i=0;i<theRevealedCards.size();i++){
    	DomCardName theCard = theRevealedCards.get(i).getName();
    	if (thePrevious!=null && theCard==thePrevious)
    	  break;
    	if (theCard.hasCardType(DomCardType.Action))
    		owner.addActions(2);
    	if (theCard.hasCardType(DomCardType.Treasure))
    		owner.addAvailableCoins(2);
    	if (theCard.hasCardType(DomCardType.Victory))
    		owner.drawCards(2);
    	thePrevious = theCard;
      }
      theLeftPlayer.discard(theRevealedCards);
    }
}