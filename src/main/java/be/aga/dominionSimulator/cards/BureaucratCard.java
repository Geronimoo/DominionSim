package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class BureaucratCard extends DomCard {
    public BureaucratCard () {
      super( DomCardName.Bureaucrat);
    }

    public void play() {
    	DomCard theSilver = owner.getCurrentGame().takeFromSupply(DomCardName.Silver);
    	if (theSilver!=null)
    	  owner.gainOnTopOfDeck(theSilver);
    	for (DomPlayer thePlayer : owner.getOpponents()) {
    	   if (thePlayer.checkDefense())
    		   continue;
    	   if (thePlayer.isHuman()) {
			   handleHumanOpponent(thePlayer);
		   } else {
			   ArrayList<DomCard> victoriesInHand = thePlayer.getCardsFromHand(DomCardType.Victory);
			   Collections.sort(victoriesInHand,SORT_FOR_DISCARDING);
			   if (victoriesInHand.isEmpty()) {
				   thePlayer.showHand();
			   } else {
				   thePlayer.putOnTopOfDeck(thePlayer.removeCardFromHand(victoriesInHand.get(0)));
			   }
		   }
    	}
    }

	private void handleHumanOpponent(DomPlayer thePlayer) {
		ArrayList<DomCard> victoriesInHand = new ArrayList<DomCard>();
		Set<DomCardName> uniqueCards = thePlayer.getUniqueCardNamesInHand();
		for (DomCardName theCard:uniqueCards) {
             if (theCard.hasCardType(DomCardType.Victory))
                 victoriesInHand.add(thePlayer.getCardsFromHand(theCard).get(0));
        }
		DomCard theChosenCard = null;
		if (victoriesInHand.size()==1) {
              theChosenCard = victoriesInHand.get(0);
        }
		if (victoriesInHand.size()>1) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : victoriesInHand)
                theChooseFrom.add(theCard.getName());
            theChosenCard = thePlayer.getCardsFromHand(owner.getEngine().getGameFrame().askToSelectOneCard("Put back a card for "+this.getName().toString(), theChooseFrom, "Mandatory!")).get(0);
        }
		if (theChosenCard!=null) {
            thePlayer.putOnTopOfDeck(thePlayer.removeCardFromHand(theChosenCard));
        }
	}
}