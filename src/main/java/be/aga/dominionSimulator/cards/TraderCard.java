package be.aga.dominionSimulator.cards;

import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class TraderCard extends DomCard {
    private DomCard lastTradedCard = null;

    public TraderCard () {
      super( DomCardName.Trader);
    }

    public void play() {
      if (!owner.getCardsInHand().isEmpty()) {
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        DomCard theCardToTrash = owner.getCardsInHand().get(0);
        for (DomCard card : owner.getCardsInHand()){
        	if ((card.getCoinCost(owner.getCurrentGame())>0 && card.getTrashPriority()<=20) 
        			|| card.getTrashPriority()==0) {
        		theCardToTrash = card;
        		break;
        	}
        }
        owner.trash(owner.removeCardFromHand( theCardToTrash ));
        for (int i=0;i<theCardToTrash.getCoinCost(owner.getCurrentGame());i++) {
            DomCard theSilver = owner.getCurrentGame().takeFromSupply( DomCardName.Silver);
            if (theSilver!=null) {
              owner.gain(theSilver);
            }
        }
      }
    }
    
    public boolean react(DomCard aCard) {
      if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + this );
      lastTradedCard = aCard;
      owner.getCurrentGame().returnToSupply( aCard );
      owner.gain(DomCardName.Silver);
      return true;
    }

     public boolean wantsToReact(DomCard aCard) {
       //TODO this way of handling Trader looks a bit dirty (= Watchtower)
       if (lastTradedCard == aCard || owner.getCurrentGame().countInSupply(DomCardName.Silver)==0)
         return false;
       if (aCard.getName().getTrashPriority(owner)<16) {
      	return true;
       } else {
     	return false;
       }
     }
     @Override
    public boolean wantsToBePlayed() {
    	if (owner.getDesiredCard(owner.getTotalPotentialCurrency(), false)==DomCardName.Cache)
    		return false;
    	return true;
    }
}