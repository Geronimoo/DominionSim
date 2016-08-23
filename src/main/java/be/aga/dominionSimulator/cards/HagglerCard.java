package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class HagglerCard extends DomCard {
    public HagglerCard () {
      super( DomCardName.Haggler);
    }

    public void play() {
      owner.addAvailableCoins(2);
    }
    
	public void haggleFor(DomCard theCard) {
    	//determine the cost of the card we're going to gain
    	DomCost theCost = theCard.getCost(owner.getCurrentGame()).add(new DomCost(-1, 0));
    	//try to gain a card that player wants according to his buy rules
        DomCardName theDesiredCard = owner.getDesiredCard(null, theCost, false, false, DomCardType.Victory);
        if (theDesiredCard==null) {
          //if no suitable card found, get the best card from supply
      	  theDesiredCard=owner.getCurrentGame().getBestCardInSupplyNotOfType(owner, DomCardType.Victory, theCost);
        }
        if (theDesiredCard!=null)
          owner.gain(theDesiredCard);
	}
}