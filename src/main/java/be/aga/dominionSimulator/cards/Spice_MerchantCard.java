package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class Spice_MerchantCard extends DomCard {
    public Spice_MerchantCard () {
      super( DomCardName.Spice_Merchant);
    }

    public void play() {
      if (!trashTreasureFromHand())
    	  return;
      if (owner.getDeckSize()==0 
    	&& owner.getActionsLeft()==0 
    	&& owner.getCardsFromHand(DomCardType.Action).isEmpty()){
    	  playForMoney();
    	  return;
      }
	  if (owner.getPlayStrategyFor(this)==DomPlayStrategy.FoolsGoldEnabler 
	   && owner.getTotalPotentialCurrency().getCoins()>=2
	   && owner.getCurrentGame().countInSupply(DomCardName.Fool$s_Gold)>1){
		 playForMoney();
		 return;
      }
	  playForCards();
    }
    
    private void playForCards() {
        owner.addActions(1);
        owner.drawCards(2);
	}

	private void playForMoney() {
    	owner.addAvailableCoins(2);
    	owner.addAvailableBuys(1);
	}

	private boolean trashTreasureFromHand() {
    	if (owner.getCardsFromHand(DomCardType.Treasure).isEmpty())
    		return false;
    	ArrayList<DomCard> theCards = owner.getCardsFromHand(DomCardType.Treasure);
		Collections.sort(theCards, SORT_FOR_TRASHING);
		if (theCards.get(0).getTrashPriority()>=16)
			return false;
	    owner.trash(owner.removeCardFromHand(theCards.get(0)));
		return true;
	}

	@Override
    public int getPlayPriority() {
    	for (DomCard card : owner.getCardsFromHand(DomCardType.Treasure)){
    		if (card.getTrashPriority()<16)
    			return super.getPlayPriority();
    	}
    	return 1000;
    }

    @Override
    public boolean wantsToBePlayed() {
        return !owner.getCardsFromHand(DomCardType.Treasure).isEmpty();
    }
}