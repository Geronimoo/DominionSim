package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

public class SpoilsCard extends DomCard {

    public SpoilsCard () {
      super( DomCardName.Spoils);
    }
    
    @Override
    public boolean wantsToBePlayed() {
        return true;
//    	return owner.removingReducesBuyingPower(this);
    }
    
    @Override
    public void play() {
    	if (owner==null)
    		return;
    	owner.addAvailableCoins(3);
        if (owner.getCardsInPlay().contains(this)) {
            owner.getCardsInPlay().remove(this);
            owner.returnToSupply(this);
        }
    }

    @Override
    public double getPotentialCoinValue() {
        return 3;
    }
}