package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class MandarinCard extends DomCard {
    public MandarinCard () {
      super( DomCardName.Mandarin);
    }

    public void play() {
    	owner.addAvailableCoins(3);
    	if (owner.getCardsInHand().isEmpty()) {
  	      if (DomEngine.haveToLog) 
            DomEngine.addToLog( owner + "'s hand is empty, so returns nothing");
          return;
    	}
    	putCardBackOnTopOfDeck();
    }
    
    private void putCardBackOnTopOfDeck() {
    	Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
    	DomCard theCardToReturn = null;
    	ArrayList<DomCard> theCardsInHand = owner.getCardsInHand();
     	if (theCardsInHand.get(0).hasCardType(DomCardType.Action)){
         	owner.putOnTopOfDeck(owner.removeCardFromHand(theCardsInHand.get(0)));
         	return;
     	}
     	for (int i=theCardsInHand.size()-1;i>=0;i--){
      	  theCardToReturn = theCardsInHand.get(i);
       	  if ( (owner.stillInEarlyGame()&& !owner.removingReducesBuyingPower(theCardToReturn))
       		|| (!owner.stillInEarlyGame()&& ableToBuyBestCardWhenReturning(theCardToReturn))){
	           	owner.putOnTopOfDeck(owner.removeCardFromHand(theCardToReturn));
	           	return;
       	  }
       	  
     	}
   		theCardToReturn=theCardsInHand.get(theCardsInHand.size()-1);
     	owner.putOnTopOfDeck(owner.removeCardFromHand(theCardToReturn));
	}

	private boolean ableToBuyBestCardWhenReturning(DomCard aCardToReturn) {
		DomCardName theDesiredCard = owner.getDesiredCard( owner.getTotalPotentialCurrency().subtract(aCardToReturn.getPotentialCurrencyValue()), false);
	    for (DomBuyRule buyRule : owner.getBuyRules()){
	      if (owner.wantsToGainOrKeep(buyRule.getCardToBuy())) {
	    	 if (theDesiredCard == buyRule.getCardToBuy())
	    		 return true;
	    	 else
	    		 return false;
	      }
	    }
	    return false;
	}

    
	@Override
	public double getPotentialCoinValue() {
		if (!owner.getCardsInHand().contains(this))
			return super.getPotentialCoinValue();
		//TODO this is not correct at all, but it needs to get handled
		if (super.getPotentialCoinValue() == 0)
		  return 0;
		if (owner.getProbableActionsLeft()==1 && owner.getCardsFromHand(DomCardType.Action).size()>1)
			return 3;
		if (owner.getCardsFromHand(DomCardType.Action).size()+owner.getCardsFromHand(DomCardType.Treasure).size()<owner.getCardsInHand().size())
			return 3;
		if (!owner.getCardsFromHand(DomCardName.Copper).isEmpty())
			return 2;
	    return 1;
	}

	@Override
	public void doWhenGained() {
		//TODO now treasures are sorted so the best will be drawn again, but might need more handling
		Collections.sort(owner.getCardsInPlay(),SORT_FOR_DISCARDING);
		while (!owner.getCardsFromPlay(DomCardType.Treasure).isEmpty()){
			DomCard theCard = owner.removeCardFromPlay(owner.getCardsFromPlay(DomCardType.Treasure).get(0));
			owner.putOnTopOfDeck(theCard);
		}
	}    
}