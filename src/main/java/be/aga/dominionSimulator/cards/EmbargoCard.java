package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class EmbargoCard extends DomCard {
    public EmbargoCard () {
      super( DomCardName.Embargo);
    }

    public void play() {
      owner.addAvailableCoins(2);
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
//    removed because it won't embargo Peddlers :(
//	  && thePlayer.getDesiredCard(theCardToBuy.getCost(thePlayer), false)==theCardToBuy
    		   && owner.getCurrentGame().getEmbargoTokensOn(theCardToBuy)==0
    		   && owner.getCurrentGame().countInSupply(theCardToBuy)>0) {
    			putEmbargoTokenOn(theRule.getCardToBuy());
    			return;
    		  }
    	  }
      }
      //if no suitable card found, just embargo a previously Embargoed card or a Curse
      putEmbargoTokenOn(owner.getCurrentGame().getBoard().getRandomCardWithEmbargoToken());
    }

	private void putEmbargoTokenOn(DomCardName cardToBuy) {
		//trashing makes owner=null, so make a local variable
		DomPlayer theOwner = owner;
        if (owner.getCardsInPlay().contains(this))
          owner.trash(owner.removeCardFromPlay(this));
		theOwner.getCurrentGame().putEmbargoTokenOn(cardToBuy);
        if (DomEngine.haveToLog) 
	      DomEngine.addToLog( theOwner + " puts an Embargo Token on " + cardToBuy.toHTML());
	}
}