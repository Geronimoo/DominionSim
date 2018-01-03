package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.*;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class EmbargoCard extends DomCard {
    public EmbargoCard () {
      super( DomCardName.Embargo);
    }

    public void play() {
      owner.addAvailableCoins(2);
      if (owner.isHumanOrPossessedByHuman()) {
		  handleHumanPlayer();
		  return;
	  }
      for (DomPlayer thePlayer : owner.getOpponents()) {
    	  //runSimulation through the buy rules of all opponents until we find a card that is not in our buy rules
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

	private void handleHumanPlayer() {
		ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
		for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
			if (owner.getCurrentGame().countInSupply(theCard)>0)
				theChooseFrom.add(theCard);
		}
		if (theChooseFrom.isEmpty())
			return;
		putEmbargoTokenOn(owner.getEngine().getGameFrame().askToSelectOneCard("Put Embargo token", theChooseFrom, "Mandatory!"));
	}

	private void putEmbargoTokenOn(DomCardName cardToEmbargo) {
		//trashing makes owner=null, so make a local variable
		DomPlayer theOwner = owner;
        if (owner.getCardsInPlay().contains(this))
          owner.trash(owner.removeCardFromPlay(this));
		theOwner.getCurrentGame().putEmbargoTokenOn(cardToEmbargo);
        if (DomEngine.haveToLog) 
	      DomEngine.addToLog( theOwner + " puts an Embargo Token on " + cardToEmbargo.toHTML());
	}
}