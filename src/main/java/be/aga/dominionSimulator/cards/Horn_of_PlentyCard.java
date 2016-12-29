package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.HashSet;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Horn_of_PlentyCard extends DomCard {
    public Horn_of_PlentyCard () {
      super( DomCardName.Horn_of_Plenty);
    }

    public void play() {
  	  HashSet<DomCardName> theSingleCards = new HashSet<DomCardName>();
      for (DomCard card : owner.getCardsInPlay()) {
    	theSingleCards.add(card.getName());
      }
      if (DomEngine.haveToLog) 
      	DomEngine.addToLog( owner + " has " + theSingleCards.size() + " different cards in play");
      DomCardName theCardToGain = owner.getDesiredCardWithRestriction(null,new DomCost(theSingleCards.size(), 0), false, DomCardName.Stonemason);
      if (theCardToGain==null) {
    	//possibly null if played by Venture
        theCardToGain=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(theSingleCards.size(), 0));
      }
      if (owner.stillInEarlyGame() && theCardToGain.hasCardType(DomCardType.Victory) && (theCardToGain.hasCardType(DomCardType.Action)||theCardToGain.hasCardType(DomCardType.Treasure)))
        theCardToGain = owner.getDesiredCard(null, new DomCost(theSingleCards.size(), 0), false, false, DomCardType.Victory);
      if (theCardToGain==null) {
         //possibly null if played by Venture
          theCardToGain=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(theSingleCards.size(), 0));
      }
      if (theCardToGain==null)
        return;
      owner.gain(theCardToGain);
      if (theCardToGain.hasCardType(DomCardType.Victory)&& owner.getCardsFromPlay(getName()).contains(this))
        owner.trash(owner.removeCardFromPlay(this));
    }
    
    @Override
    public boolean wantsToBePlayed() {
    	  HashSet<DomCardName> theSingleCards = new HashSet<DomCardName>();
  		  theSingleCards.add(getName());
          for (DomCard card : owner.getCardsInPlay()) {
        	  theSingleCards.add(card.getName());
          }
          DomCardName theCardToGain = owner.getDesiredCard(new DomCost(theSingleCards.size(), 0), false);
          return theCardToGain != null;
    }
}