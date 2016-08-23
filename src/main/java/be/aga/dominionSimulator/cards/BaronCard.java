package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class BaronCard extends DomCard {
    public BaronCard () {
      super( DomCardName.Baron);
    }

    public void play() {
        owner.addAvailableBuys( 1 );
        if (owner.discardFromHand(DomCardName.Estate)){
          owner.addAvailableCoins( 4 );
        } else {
          DomCard theEstate = owner.getCurrentGame().takeFromSupply(DomCardName.Estate);
          if (theEstate!=null)
            owner.gain(theEstate);
        }
    }

    public boolean wantsToBePlayed() {
      return owner.getCardsFromHand( DomCardName.Estate).size()>0
          || owner.wants( DomCardName.Estate)
          || owner.getCurrentGame().countInSupply( DomCardName.Estate )==0;
    }
    
    @Override
    public int getDiscardPriority(int aActionsLeft) {
    	if (aActionsLeft>0 && owner.getCardsInHand().contains(this) 
    	&& owner.getCardsFromHand(DomCardName.Baron).size()==1 && !owner.getCardsFromHand(DomCardName.Estate).isEmpty())
    		return 29;
    	return super.getDiscardPriority(aActionsLeft);
    }

}