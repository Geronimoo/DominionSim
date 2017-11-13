package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class TaxCard extends DomCard {
    public TaxCard() {
      super( DomCardName.Tax);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman()) {
      	handleHuman();
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
    		   && owner.getCurrentGame().countInSupply(theCardToBuy)>0) {
    			putTaxOn(theRule.getCardToBuy());
    			return;
    		  }
    	  }
      }
      //if no suitable card found, just tax a previously Embargoed card or a Curse
      putTaxOn(owner.getCurrentGame().getBoard().getRandomCardWithEmbargoToken());
    }

	private void handleHuman() {
		ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
		for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
			if (owner.getCurrentGame().countInSupply(theCard)>0)
				theChooseFrom.add(theCard);
		}
		if (theChooseFrom.isEmpty())
			return;
		putTaxOn(owner.getEngine().getGameFrame().askToSelectOneCard("Put Tax", theChooseFrom, "Mandatory!"));
	}

	private void putTaxOn(DomCardName cardToBuy) {
		owner.getCurrentGame().getBoard().putTaxOn(cardToBuy, 2);
        if (DomEngine.haveToLog) 
	      DomEngine.addToLog( owner + " puts tax on " + cardToBuy.toHTML());
	}
}