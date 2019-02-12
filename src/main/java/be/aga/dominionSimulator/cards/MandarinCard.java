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
    	if (owner.getCardsInHand().isEmpty())
    		return;
    	if (owner.isHumanOrPossessedByHuman()) {
    		handleHuman();
    		return;
		}
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

	private void handleHuman() {
		ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
		for (DomCard theCard : owner.getCardsInHand()) {
			theChooseFrom.add(theCard.getName());
		}
		if (theChooseFrom.isEmpty())
			return;
		DomCardName theCardToPutBack = owner.getEngine().getGameFrame().askToSelectOneCard("Put on deck", theChooseFrom, "Mandatory!");
		owner.putOnTopOfDeck(owner.removeCardFromHand(owner.getCardsFromHand(theCardToPutBack).get(0)));
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
    	if (owner.isHumanOrPossessedByHuman()) {
			ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
			ArrayList<DomCard> theTreasures = new ArrayList<DomCard>();
			theTreasures.addAll(owner.getCardsFromPlay(DomCardType.Treasure));
			owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>", theTreasures , theChosenCards, 0);
			if (theChosenCards.size() < theTreasures.size()) {
				for (DomCard theCard : theTreasures) {
					owner.putOnTopOfDeck(theCard);
					owner.removeCardFromPlay(theCard);
				}
			} else {
				for (int i = theChosenCards.size() - 1; i >= 0; i--) {
					for (DomCard theCard : theTreasures) {
						if (theChosenCards.get(i).getName() == theCard.getName()) {
							owner.putOnTopOfDeck(theCard);
							owner.removeCardFromPlay(theCard);
							theTreasures.remove(theCard);
							break;
						}
					}
				}
			}
		}else {
//        while (!owner.getCardsFromPlay(DomCardType.Treasure).isEmpty() && theSum<5){
//            DomCard theCard = owner.getCardsFromPlay(DomCardType.Treasure).get(0);
//            theSum+=theCard.getCoinValue();
//            owner.putOnTopOfDeck(owner.removeCardFromPlay(theCard));
//        }
//        TODO now treasures are sorted so the best will be drawn again, but might need more handling
		  Collections.sort(owner.getCardsInPlay(), SORT_FOR_DISCARDING);
		  while (!owner.getCardsFromPlay(DomCardType.Treasure).isEmpty()) {
		    DomCard theCard = owner.removeCardFromPlay(owner.getCardsFromPlay(DomCardType.Treasure).get(0));
			owner.putOnTopOfDeck(theCard);
		  }
		}
	}

	@Override
	public boolean hasCardType(DomCardType aType) {
		if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
			return true;
		return super.hasCardType(aType);
	}

}