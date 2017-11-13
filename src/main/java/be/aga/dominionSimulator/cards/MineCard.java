package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class MineCard extends DomCard {
    private DomCard myCardToTrash;
	private DomCardName myDesiredCard;

	public MineCard () {
      super( DomCardName.Mine);
    }

    public void play() {
		if (owner.isHumanOrPossessedByHuman()) {
			handleHumanPlayer();
		} else {
			if (owner.getPlayStrategyFor(this) == DomPlayStrategy.mineCopperFirst && !owner.getCardsFromHand(DomCardName.Copper).isEmpty() && owner.getCurrentGame().countInSupply(DomCardName.Silver) > 0) {
				owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Copper).get(0)));
				owner.gainInHand(DomCardName.Silver);
				return;
			}
			checkForCardToMine();
			if (myCardToTrash == null)
				//possible if played by Golem for instance
				return;
			owner.trash(owner.removeCardFromHand(myCardToTrash));
			if (myDesiredCard == null)
				//possible if card was throne roomed
				myDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, DomCardType.Treasure, myCardToTrash.getCost(owner.getCurrentGame()).add(new DomCost(3, 0)));
			if (myDesiredCard != null)
				owner.gainInHand(myDesiredCard);
		}
    }

	private void handleHumanPlayer() {
		ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
		for (DomCard theCard : owner.getCardsFromHand(DomCardType.Treasure))
            theChooseFrom.add(theCard.getName());
		if (theChooseFrom.isEmpty())
            return;
		DomCard theCardToMine = owner.getCardsFromHand(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to " + this.getName().toString(), theChooseFrom, "Mandatory!")).get(0);
		owner.trash(owner.removeCardFromHand(theCardToMine));
		theChooseFrom = new ArrayList<DomCardName>();
		for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
            if (theCard.getCost(owner.getCurrentGame()).compareTo(theCardToMine.getCost(owner.getCurrentGame()).add(new DomCost(3, 0))) <= 0 && theCard.hasCardType(DomCardType.Treasure) && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
		if (theChooseFrom.isEmpty())
            return;
		owner.gainInHand(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain from " + this.getName().toString(), theChooseFrom, "Mandatory!"));
	}

	@Override
    public boolean wantsToBePlayed() {
    	checkForCardToMine();
    	return myDesiredCard!=null;
    }

	private void checkForCardToMine() {
		Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
		DomCardName thePossibleDesiredCard=null;
		myDesiredCard=null;
		myCardToTrash=null;
    	//we try to get the best treasure which is important in Colony games (Golds into Platinums and not Silvers into Golds)
    	for (DomCard card : owner.getCardsFromHand(DomCardType.Treasure)){
	        DomCost theCostOfmyDesiredCard = card.getName().getCost(owner.getCurrentGame()).add(new DomCost(3,0)) ;
    		thePossibleDesiredCard=owner.getDesiredCard(DomCardType.Treasure, theCostOfmyDesiredCard , false, false, null);
    		if (thePossibleDesiredCard==null)
    			continue;
    		if ((card.getName()!=thePossibleDesiredCard || thePossibleDesiredCard==DomCardName.Ill_Gotten_Gains)) {
	    		if (myCardToTrash==null
	    		 || (thePossibleDesiredCard.getOrderInBuyRules(owner) <= card.getName().getOrderInBuyRules(owner)
	    			  && (thePossibleDesiredCard.getOrderInBuyRules(owner) < myDesiredCard.getOrderInBuyRules(owner)
	                    || (thePossibleDesiredCard.getOrderInBuyRules(owner) == myDesiredCard.getOrderInBuyRules(owner)
	                     && myCardToTrash.getTrashPriority()>card.getTrashPriority())))){
	    			myDesiredCard=thePossibleDesiredCard;
	    			myCardToTrash=card;
	    		}
    		}
     	}
	}
}