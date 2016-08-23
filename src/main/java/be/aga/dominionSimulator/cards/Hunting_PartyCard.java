package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Hunting_PartyCard extends DomCard {
    public Hunting_PartyCard () {
      super( DomCardName.Hunting_Party);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
//      owner.showHand();
      ArrayList<DomCard> theRevealedCards = new ArrayList<DomCard>();
      for (;;){
        ArrayList<DomCard> theTopCard = owner.revealTopCards(1);
        if (theTopCard.isEmpty())
    	  break;
        if (!hasDuplicateInHand(theTopCard.get(0))){
        	owner.putInHand(theTopCard.get(0));
        	break;
        } else {
			theRevealedCards.add(theTopCard.get(0));
        }
      }
      owner.discard(theRevealedCards);
    }

	public boolean hasDuplicateInHand(DomCard aCard) {
        for (DomCard theCard : owner.getCardsInHand()) {
        	if (theCard.getName()==aCard.getName())
        		return true;
        }
		return false;
	}
}