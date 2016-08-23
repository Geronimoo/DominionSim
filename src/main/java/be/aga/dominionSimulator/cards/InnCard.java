package be.aga.dominionSimulator.cards;

import java.util.HashSet;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class InnCard extends DomCard {
    public InnCard () {
      super( DomCardName.Inn);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(2);
      owner.doForcedDiscard(2, false);
    }
    
    @Override
    public void doWhenGained() {
    	HashSet<DomCard> theCards = new HashSet<DomCard>();
		for (DomCard theCard : owner.getDeck().getDiscardPile()){
			if (theCard.hasCardType(DomCardType.Action) && theCard.getDiscardPriority(1)>10)
			  theCards.add(theCard);
		}
		for (DomCard card : theCards){
		    owner.getDeck().getDiscardPile().remove(card);
		    owner.putOnTopOfDeck(card);
		}
		owner.shuffleDeck();
    }
}