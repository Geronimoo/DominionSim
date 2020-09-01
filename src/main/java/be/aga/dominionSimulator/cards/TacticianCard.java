package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class TacticianCard extends DomCard {
    private boolean durationFailed=false;

    public TacticianCard () {
      super( DomCardName.Tactician);
    }

    public void play() {
      durationFailed = true;
      if (owner.getCardsInHand().size()>0) {
        owner.discardHand();
        durationFailed=false;
      }
    }

    public void resolveDuration() {
      owner.drawCards( 5 );
      owner.addAvailableBuys(1);
      owner.addActions(1);
    }
    
    @Override
    public int getPlayPriority() {
    	if (owner.getActionsAndVillagersLeft()>1){
    		return 100;
    	}
    	return super.getPlayPriority();
    }
    @Override
    public boolean wantsToBePlayed() {
    	if (owner.getPlayStrategyFor(this)==DomPlayStrategy.standard)
    		return true;
    	for (DomBuyRule buyRule : owner.getBuyRules()){
    		if (owner.wantsToGainOrKeep(buyRule.getCardToBuy())){ 
    		  if ( owner.getDesiredCard(owner.getTotalPotentialCurrency(), false)==buyRule.getCardToBuy())
    			return false;
    		  else
      			return true;
    		}
    	}
    	return true;
    }

    @Override
    public boolean durationFailed() {
        return durationFailed;
    }
}