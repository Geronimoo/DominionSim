package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

public class Will_o$_WispCard extends DomCard {
    public Will_o$_WispCard() {
      super( DomCardName.Will_o$_Wisp);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      if (owner.getDeckAndDiscardSize()==0)
    	return;
      DomCard theRevealedCard = owner.revealTopCards(1).get(0);
	  if (new DomCost(2,0).customCompare(theRevealedCard.getCost(owner.getCurrentGame()))>=0) {
        owner.putInHand(theRevealedCard);
	  }else{
        owner.putOnTopOfDeck(theRevealedCard);
      }
    }
}