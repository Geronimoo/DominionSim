package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Opulent_CastleCard extends DomCard {
    public Opulent_CastleCard() {
      super( DomCardName.Opulent_Castle);
    }

    public void play() {
        int amount = 0;
        while (!owner.getCardsFromHand(DomCardType.Victory).isEmpty()) {
            owner.discardFromHand(owner.getCardsFromHand(DomCardType.Victory).get(0));
            amount+=2;
        }
        if (amount>0)
            owner.addAvailableCoins( amount );
    }

    @Override
    public int getDiscardPriority(int aActionsLeft) {
    	if (aActionsLeft>0 && owner.getCardsInHand().contains(this) 
    	&& owner.getCardsFromHand(DomCardName.Opulent_Castle).size()==1 && owner.getCardsFromHand(DomCardType.Victory).size()>1)
    		return 29;
    	return super.getDiscardPriority(aActionsLeft);
    }

}