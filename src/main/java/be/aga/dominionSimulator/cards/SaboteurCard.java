package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class SaboteurCard extends DomCard {
    public SaboteurCard () {
      super( DomCardName.Saboteur);
    }

    public void play() {
      for (DomPlayer thePlayer : owner.getOpponents()) {
        if (thePlayer.checkDefense() )
        	continue;
        ArrayList< DomCard > theCards = thePlayer.revealUntilCost(3);
        for (DomCard card : theCards) {
    	   if (card.getCoinCost(owner.getCurrentGame())>=3){
    		   DomCost theCost = new DomCost(card.getCoinCost(owner.getCurrentGame())-2, card.getPotionCost());
    		   thePlayer.trash(card);
    		   DomCardName theDesiredCard = thePlayer.getDesiredCard(theCost, false);
    		   if (theDesiredCard!=null)
    			   thePlayer.gain(theDesiredCard);
    		   theCards.remove(card);
    		   break;
    	   }
        }
        thePlayer.discard(theCards);
      }
    }
}