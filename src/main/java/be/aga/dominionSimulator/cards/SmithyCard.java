package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class SmithyCard extends DomCard {
    public SmithyCard () {
      super( DomCardName.Smithy);
    }

    @Override
    public void play() {
      owner.drawCards( 3 );
    }
    
    @Override
    public int getPlayPriority() {
      return owner.getActionsLeft()>1 ? 6 : super.getPlayPriority();
    }
    
    @Override
    public boolean wantsToBePlayed() {
    	if (owner.getPlayStrategyFor(this)==DomPlayStrategy.playIfNotBuyingTopCard 
    	  && owner.isGoingToBuyTopCardInBuyRules(owner.getTotalPotentialCurrency()))
    	  return false;

    	return super.wantsToBePlayed();
    }
}