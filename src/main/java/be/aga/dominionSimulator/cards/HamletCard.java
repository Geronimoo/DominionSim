package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class HamletCard extends DomCard {
	private boolean discardedForAction;
	private boolean discardedForBuy;

    public HamletCard () {
      super( DomCardName.Hamlet);
    }

    public void play() {
    	reset();
        owner.addActions(1);
        owner.drawCards(1);
        ArrayList<DomCard> cardsInHand = owner.getCardsInHand();
        if (cardsInHand.isEmpty())
      	  return;
  	    Collections.sort(cardsInHand, SORT_FOR_DISCARD_FROM_HAND);
  	    //fix for Menagerie which will often be played in combo with Hamlet
        if (!owner.getCardsFromHand(DomCardName.Menagerie).isEmpty()){
          processMenagerie();
        }
        //add an extra action if it's needed (multiple terminals in hand)
        if (!discardedForAction && !owner.getCardsFromHand(DomCardType.Action).isEmpty()){
        	if (owner.getProbableActionsLeft()<0){
        		discardForExtraAction(cardsInHand.get(0));
        	}
        }
        //if we're going to draw a bunch of cards later in the turn, we want to make sure we have enough actions
        if (!owner.getCardsFromHand(DomCardType.Card_Advantage).isEmpty() && owner.getActionsLeft()==1 && !discardedForAction){
          discardForExtraAction(cardsInHand.get(0));
        }
  	    //fix for Library/Watchtower which will often be played in combo with Hamlet
        if (!owner.getCardsFromHand(DomCardName.Library).isEmpty() 
         || !owner.getCardsFromHand(DomCardName.Watchtower).isEmpty()
         || !owner.getCardsFromHand(DomCardName.Jack_of_all_Trades).isEmpty()){
    	  discardForLibraryAndWatchtower(cardsInHand);
        }
        
        //possibly discard for an extra buy if we have no actions in hand and no extra buys yet
        if (owner.getBuysLeft()<2 && owner.getCardsFromHand(DomCardType.Action).isEmpty()){
          processNoOtherActionsInHand(cardsInHand);
        }
        
        //in a Gardens strategy, try to get extra buys
        if (!discardedForBuy) {
          checkForGardens();	
        }
        
        //we might have Duchies, Provinces and others in hand that we have no use for, so just discard them
        discardGarbage(cardsInHand);
    }

	private void checkForGardens() {
		for (DomBuyRule rule : owner.getBuyRules()){
			if (rule.getCardToBuy()==DomCardName.Gardens) {
		        for (int i=0;i<owner.getCardsInHand().size();i++) {
		          DomCard theCardInHand = owner.getCardsInHand().get(i);
		      	  if (!owner.removingReducesBuyingPower(theCardInHand) 
		      	   && !theCardInHand.hasCardType(DomCardType.Action)){
		      		  discardForExtraBuy(theCardInHand);
		      		  return;
		      	  }
		        }
		        return;
			}
		}
	}

	private void discardForLibraryAndWatchtower(ArrayList<DomCard> cardsInHand) {
		while (!cardsInHand.isEmpty()&&
		      (!discardedForAction || !discardedForBuy )
		      && cardsInHand.get(0).getDiscardPriority(1)<30
		      && cardsInHand.get(0).getName()!=DomCardName.Library
		      && cardsInHand.get(0).getName()!=DomCardName.Watchtower
			  && cardsInHand.get(0).getName()!=DomCardName.Jack_of_all_Trades){
			if (!discardedForAction){
				discardForExtraAction(cardsInHand.get(0));
			} else {
				if (!discardedForBuy){
					discardForExtraBuy(cardsInHand.get(0));
				}
			}
		}
	}

	private void discardGarbage(ArrayList<DomCard> cardsInHand) {
		while (!cardsInHand.isEmpty()&&
        	  (!discardedForAction || !discardedForBuy )
        	  && cardsInHand.get(0).getDiscardPriority(1)<10){
	    	if (!discardedForAction){
	    		discardForExtraAction(cardsInHand.get(0));
	    	} else {
	    		if (!discardedForBuy){
	    			discardForExtraBuy(cardsInHand.get(0));
	    		}
	    	}
        }
	}

	private void reset() {
		discardedForAction=false;
		discardedForBuy=false;
	}

	private void processMenagerie() {
		MenagerieCard theMenagerie = (MenagerieCard) owner.getCardsFromHand(DomCardName.Menagerie).get(0);
		ArrayList<DomCard> theCardsToDiscard = DomPlayer.getMultiplesInHand(theMenagerie);
		if (theCardsToDiscard.isEmpty())
			return;
		Collections.sort(theCardsToDiscard, SORT_FOR_DISCARDING);
		while (!theCardsToDiscard.isEmpty() && (!discardedForAction || !discardedForBuy)){
			if (!discardedForAction){
				discardForExtraAction(theCardsToDiscard.remove(0));
			} else {
				if (!discardedForBuy){
					discardForExtraBuy(theCardsToDiscard.remove(0));
				}
			}
		}
	}

	private void processNoOtherActionsInHand(ArrayList<DomCard> cardsInHand) {
        for (int i=0;i<cardsInHand.size();i++) {
          DomCard theCardInHand = cardsInHand.get(i);
    	  if (!owner.removingReducesBuyingPower(theCardInHand)){
    		  discardForExtraBuy(theCardInHand);
    		  return;
    	  }
        }
	}
    
	private void discardForExtraAction(DomCard domCard) {
		owner.discardFromHand(domCard);
		owner.addActions(1);
		discardedForAction=true;
	}

	private void discardForExtraBuy(DomCard domCard) {
		owner.discardFromHand(domCard);
		owner.addAvailableBuys(1);
		discardedForBuy=true;
	}
}