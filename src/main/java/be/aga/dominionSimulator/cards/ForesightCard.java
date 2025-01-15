package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class ForesightCard extends DomCard {
    public ForesightCard() {
      super( DomCardName.Foresight);
    }

    public void play() {
      ArrayList<DomCard> theRevealedCards = new ArrayList<DomCard>();
      for (;;){
        ArrayList<DomCard> theTopCard = owner.revealTopCards(1);
        if (theTopCard.isEmpty())
    	  break;
        if (theTopCard.get(0).hasCardType(DomCardType.Action)){
        	owner.setAsideForForesight(theTopCard.get(0));
        	break;
        } else {
			theRevealedCards.add(theTopCard.get(0));
        }
      }
      owner.discard(theRevealedCards);
    }
}