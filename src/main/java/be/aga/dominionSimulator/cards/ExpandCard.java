package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class ExpandCard extends DomCard {
    public ExpandCard () {
      super( DomCardName.Expand);
    }

    public void play() {
      if (owner.getCardsInHand().isEmpty())
    	return;
      DomCard theCardToTrash = findCardToTrash();
      if (theCardToTrash==null) {
        //this is needed when card is played with Throne Room effect
        Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
        theCardToTrash=owner.getCardsInHand().get(0);
      }
      owner.trash(owner.removeCardFromHand( theCardToTrash));
      DomCost theMaxCostOfCardToGain = new DomCost( theCardToTrash.getCoinCost(owner.getCurrentGame()) + 3, theCardToTrash.getPotionCost());
	  DomCardName theDesiredCard = owner.getDesiredCard(theMaxCostOfCardToGain, false);
      if (theDesiredCard==null)
    	theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theMaxCostOfCardToGain);
      if (theDesiredCard!=null)
        owner.gain(theDesiredCard);
    }
    
    private DomCard findCardToTrash() {
    	ArrayList<DomCard> theCardsToConsiderTrashing=new ArrayList<DomCard>();
    	ArrayList<DomCardName> theCardsToGain=new ArrayList<DomCardName>();
    	DomCardName theDesiredCardIfExpandNotUsed = owner.getDesiredCard(owner.getTotalPotentialCurrency(),false);
        for (int i=0;i<owner.getCardsInHand().size();i++) {
            if (owner.getCardsInHand().get(i)==this)
                continue;
        	//temporarily remove the card from hand AND deck
        	DomCard theCard = owner.getCardsInHand().remove(i);
            DomCost theMaxCostOfCardToGain = new DomCost( theCard.getCoinCost(owner.getCurrentGame()) + 3, theCard.getPotionCost());
        	owner.getDeck().get(theCard.getName()).remove(theCard );
      	    DomCardName theExpandGainCard = owner.getDesiredCard(theMaxCostOfCardToGain, false);
        	DomCardName theDesiredCard = owner.getDesiredCard(owner.getTotalPotentialCurrency(), false);
        	//first we will make a list of cards we consider good candidates for trashing
        	//only add to the list if:
        	//  -what we will gain is better than the card we trash (so of course it's not null)
        	//  -(and the card we will gain is better than what we were able to buy without using Expand
        	//    or -trashing the card will not hinder our buying potential)
            if (   (theExpandGainCard!=null 
            	  && theExpandGainCard.getTrashPriority(owner)>theCard.getName().getTrashPriority(owner)
            	  && (theDesiredCardIfExpandNotUsed == null
            	  || theExpandGainCard.getTrashPriority(owner)>=theDesiredCardIfExpandNotUsed.getTrashPriority(owner)
            	  || theDesiredCard==theDesiredCardIfExpandNotUsed))){ 
				theCardsToConsiderTrashing.add(theCard);
				theCardsToGain.add(theExpandGainCard);
            }
        	owner.getDeck().get(theCard.getName()).add(theCard );
        	owner.getCardsInHand().add(i, theCard);
        }
        //nothing good found
        if (theCardsToConsiderTrashing.isEmpty())
        	return null;
        //now we scan the lists to find the best possible trashing candidate
        DomCardName theBestCardToGain=null;
        DomCard theBestCardToTrash=null;
        for (int i=0;i<theCardsToGain.size();i++) {
          DomCardName theCardToGain = theCardsToGain.get(i);
          if (owner.stillInEarlyGame()){
	    	if (theBestCardToGain==null
	        || theCardsToConsiderTrashing.get(i).getTrashPriority()<theBestCardToTrash.getTrashPriority()) {
	    	    theBestCardToGain=theCardToGain;
	    	    theBestCardToTrash=theCardsToConsiderTrashing.get(i);
	    	}
	      } else {
	    	  if (theBestCardToGain==null
	      	   //trashing this card will give us a better card
	    	   || theCardToGain.getTrashPriority(owner)>theBestCardToGain.getTrashPriority(owner)
	           //trashing this card is more desirable while still allowing us to gain the best card
	    	   || ((theCardToGain.getTrashPriority(owner)==theBestCardToGain.getTrashPriority(owner)
	               && theCardsToConsiderTrashing.get(i).getTrashPriority()<theBestCardToTrash.getTrashPriority()))) {
	    	    theBestCardToGain=theCardToGain;
	    	    theBestCardToTrash=theCardsToConsiderTrashing.get(i);
	    	  }
	      }
        }
        return theBestCardToTrash;
    }

    @Override
    public boolean wantsToBePlayed() {
      return findCardToTrash()!=null;
   }
}