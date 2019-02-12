package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class BaronCard extends DomCard {
    public BaronCard () {
      super( DomCardName.Baron);
    }

    public void play() {
        owner.addAvailableBuys( 1 );
        if (owner.isHumanOrPossessedByHuman()) {
            if (owner.getCardsFromHand(DomCardName.Estate).isEmpty()) {
              owner.gain(DomCardName.Estate);
            }else {
                if (owner.getEngine().getGameFrame().askPlayer("<html>Discard " + DomCardName.Estate.toHTML() + " ?</html>", "Resolving " + this.getName().toString())) {
                    owner.discardFromHand(DomCardName.Estate);
                    owner.addAvailableCoins(4);
                } else {
                    owner.gain(DomCardName.Estate);
                }
            }
        }else {
            if (owner.discardFromHand(DomCardName.Estate)) {
                owner.addAvailableCoins(4);
            } else {
                DomCard theEstate = owner.getCurrentGame().takeFromSupply(DomCardName.Estate);
                if (theEstate != null)
                    owner.gain(theEstate);
            }
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

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}