package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class MintCard extends DomCard {
    public MintCard () {
      super( DomCardName.Mint);
    }

    public void play() {
      DomCardName theCardToCopy=findCardToCopy();
      if (theCardToCopy!=null) {
        if (DomEngine.haveToLog) 
          DomEngine.addToLog( owner+ " reveals " +theCardToCopy + " from hand");
    	owner.gain(theCardToCopy);
      } else {
        if (DomEngine.haveToLog) 
          DomEngine.addToLog( " but does not find a suitable card to Mint");
      }
    }

    private DomCardName findCardToCopy() {
    	for (DomBuyRule buyRule : owner.getBuyRules()){
    	  DomCardName theCard = buyRule.getCardToBuy();
    	  if (theCard.hasCardType(DomCardType.Treasure)
    	   && owner.wants(theCard) 
    	   && !owner.getCardsFromHand(theCard).isEmpty()) 
            return theCard;
    	}
		return null;
    }
    
	@Override
    public boolean wantsToBePlayed() {
      return findCardToCopy()!=null;
    }
}