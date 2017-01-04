package be.aga.dominionSimulator.cards;

import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class ChapelCard extends DomCard {
    public ChapelCard () {
      super( DomCardName.Chapel);
    }

    public void play() {
    	int theMin$Indeck = owner.getPlayStrategyFor(this)==DomPlayStrategy.aggressiveTrashing ? 4 : 6;
    	int theTrashOverBuyTreshold = owner.getPlayStrategyFor(this)==DomPlayStrategy.aggressiveTrashing ? 3 : 4;
        int theTrashCount=0;
        for (DomCard theCard : owner.getCardsInHand()) {
          if (theCard.getTrashPriority()<16) {
            theTrashCount++;
          }
        }
        Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
        for ( int i=0; i<4 && !owner.getCardsInHand().isEmpty();i++) {
          DomCard theCardToTrash=owner.getCardsInHand().get( 0 );
          if (theCardToTrash.getTrashPriority()>=16 
           || (owner.removingReducesBuyingPower( theCardToTrash ) && theTrashCount< theTrashOverBuyTreshold)
           || owner.getTotalMoneyInDeck()-theCardToTrash.getPotentialCoinValue() < theMin$Indeck ) {
            return ;
          }
          owner.trash(owner.removeCardFromHand( theCardToTrash));
        }
    } 
    
    @Override
    public int getPlayPriority() {
    	if (owner.getPlayStrategyFor(this)!=DomPlayStrategy.aggressiveTrashing)
    		return super.getPlayPriority();
    		
        int theTrashCount=0;
        for (DomCard theCard : owner.getCardsInHand()) {
          if (theCard==this)
        	  continue;
          if (theCard.getTrashPriority()<16) {
            theTrashCount++;
          }
        }
        if (theTrashCount>1 && owner.getActionsLeft()==1)
          return 18;
        return super.getPlayPriority();
    }

    @Override
    public boolean wantsToBeMultiplied() {
        return false;
    }
}