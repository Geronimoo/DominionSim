package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class TaxCard extends DomCard {
    public TaxCard() {
      super( DomCardName.Tax);
    }

    public void play() {
      for (DomPlayer thePlayer : owner.getOpponents()) {
    	  //run through the buy rules of all opponents until we find a card that is not in our buy rules 
    	  for (DomBuyRule theRule : thePlayer.getBuyRules()) {
    		  DomCardName theCardToBuy = theRule.getCardToBuy();
    		  boolean sameCardFound=false;
    		  for (DomBuyRule ownerRule : owner.getBuyRules()) {
    			  if (theCardToBuy==ownerRule.getCardToBuy()) {
    			    sameCardFound = true;
    			    break;
    			  }
    		  }
    		  if (!sameCardFound
    		   && owner.getCurrentGame().countInSupply(theCardToBuy)>0) {
    			putTaxOn(theRule.getCardToBuy());
    			return;
    		  }
    	  }
      }
      //if no suitable card found, just embargo a previously Embargoed card or a Curse
      putTaxOn(owner.getCurrentGame().getBoard().getRandomCardWithEmbargoToken());
    }

	private void putTaxOn(DomCardName cardToBuy) {
		owner.getCurrentGame().getBoard().putTaxOn(cardToBuy, 2);
        if (DomEngine.haveToLog) 
	      DomEngine.addToLog( owner + " puts tax on " + cardToBuy.toHTML());
	}
}