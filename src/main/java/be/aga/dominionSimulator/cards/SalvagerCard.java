package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

public class SalvagerCard extends DomCard {

    public SalvagerCard () {
      super( DomCardName.Salvager);
    }

    public void play() {
        owner.addAvailableBuys( 1 );
        if (owner.getCardsInHand().isEmpty())
          return;
        DomCard theCardToTrash=findCardToTrash();
        if (theCardToTrash==null) {
          //this is needed when card is played with Throne Room effect
          Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
          theCardToTrash=owner.getCardsInHand().get(0);
        }
        owner.trash(owner.removeCardFromHand( theCardToTrash));
        owner.addAvailableCoins( theCardToTrash.getCost(owner.getCurrentGame()).getCoins() );
    }

    private DomCard findCardToTrash() {
    	ArrayList<DomCard> theCardsToConsiderTrashing=new ArrayList<DomCard>();
    	ArrayList<DomCardName> theCardsToGain=new ArrayList<DomCardName>();
    	DomCardName theDesiredCardIfSalvagerNotUsed = owner.getDesiredCard(owner.getTotalPotentialCurrency(),false);
        for (int i=0;i<owner.getCardsInHand().size();i++) {
            if (owner.getCardsInHand().get(i)==this)
                continue;
        	//temporarily remove the card from hand AND deck
        	DomCard theCard = owner.getCardsInHand().remove(i);
        	owner.getDeck().get(theCard.getName()).remove(theCard );
        	DomCost theGain = theCard.getCost(owner.getCurrentGame());
        	DomCardName theDesiredCard = owner.getDesiredCard(owner.getTotalPotentialCurrency().add(theGain), false);
        	//first we will make a list of cards we consider good candidates for trashing
        	//only add to the list if:
        	//  -we will be able to buy something (not null)
        	//  -and the card we will be able to buy is better than what we were able to buy without using Salvager
        	//  -and the card we will be able to buy is better than the card we are about to trash
        	// OR
        	//  -we want to trash the card and it will not have an influence on our buying power
            if (   (theDesiredCard!=null
                && (theDesiredCardIfSalvagerNotUsed==null || theDesiredCard.getTrashPriority(owner)>theDesiredCardIfSalvagerNotUsed.getTrashPriority(owner)) 
    		    && theCard.getName().getTrashPriority(owner)<theDesiredCard.getTrashPriority(owner))
    		 ||    (theCard.getName().getTrashPriority(owner)<16
                && (theDesiredCard==theDesiredCardIfSalvagerNotUsed || theDesiredCardIfSalvagerNotUsed==null))) {
				theCardsToConsiderTrashing.add(theCard);
				theCardsToGain.add(theDesiredCard);
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
          if (theCardToGain!=null) {
        	  if (theBestCardToGain==null
          	   //trashing this card will allow us to buy a better card
        	   || theCardToGain.getTrashPriority(owner)>theBestCardToGain.getTrashPriority(owner)
               //trashing this card is more desirable while still allowing us to buy the best card
        	   || ((theCardToGain.getTrashPriority(owner)==theBestCardToGain.getTrashPriority(owner)
                   && theCardsToConsiderTrashing.get(i).getTrashPriority()<theBestCardToTrash.getTrashPriority()))) {
        	    theBestCardToGain=theCardToGain;
        	    theBestCardToTrash=theCardsToConsiderTrashing.get(i);
        	  }
          }	  
        }
        //joy, we found a better card to gain, so also something to trash
        if (theBestCardToTrash!=null)
          return theBestCardToTrash;

        //we didn't find a better card to gain, but we might still find something we want to trash 
        Collections.sort(theCardsToConsiderTrashing, SORT_FOR_TRASHING);
        return theCardsToConsiderTrashing.get(0);
    }

    public boolean wantsToBePlayed() {
//        return true;
        return findCardToTrash()!=null;
    }
}