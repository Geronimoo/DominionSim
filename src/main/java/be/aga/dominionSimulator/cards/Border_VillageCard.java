package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class Border_VillageCard extends DomCard {
    public Border_VillageCard () {
      super( DomCardName.Border_Village);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(1);
    }
    
    @Override
    public void doWhenGained() {
    	//determine the cost of the card we're going to gain
    	DomCost theCost = getCost(owner.getCurrentGame()).add(new DomCost(-1, 0));
    	if (owner.isHumanOrPossessedByHuman()) {
    	    handleHuman(theCost);
    	    return;
        }
    	//try to gain a card that player wants according to his buy rules
        DomCardName theDesiredCard = owner.getDesiredCardWithRestriction(null,theCost,false,DomCardName.Stonemason);
        if (theDesiredCard==null) {
          //if no suitable card found, get the best card from supply
      	  theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theCost);
        }
        if (theDesiredCard!=null)
          owner.gain(theDesiredCard);
    }

    private void handleHuman(DomCost theCost) {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theCost.customCompare(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gain(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain from "+this.getName().toString(), theChooseFrom, "Mandatory!"));
    }

    @Override
    public boolean wantsToBePlayed() {
    	int numberOfBorderVillagesInHand = owner.getCardsFromHand(DomCardName.Border_Village).size();
		int numberOfActionsInHand = owner.getCardsFromHand(DomCardType.Action).size();
		int numberOfTrashForBenefitInHand = owner.getCardsFromHand(DomCardType.TrashForBenefit).size();
		if (numberOfBorderVillagesInHand==1 && numberOfTrashForBenefitInHand==1 && numberOfActionsInHand==2) {
			if (!owner.getCardsFromHand(DomCardName.Upgrade).isEmpty()
			 || !owner.getCardsFromHand(DomCardName.Remake).isEmpty()
			 || !owner.getCardsFromHand(DomCardName.Develop).isEmpty() ){
				if (owner.getDesiredCard(getCost(owner.getCurrentGame()).add(new DomCost(1, 0)), true)==null)
					return true;
			}
			return false;
		}
		return super.wantsToBePlayed();
    }
    @Override
    public int getTrashPriority() {
    	if (owner==null)
    		return super.getTrashPriority();
    	if (!owner.getCardsInPlay().isEmpty() 
    	  && owner.getCardsInPlay().get(owner.getCardsInPlay().size()-1).hasCardType(DomCardType.TrashForBenefit))
    		//if last played card is a trash for benefit, make sure to trash this first!
    		return 0;
    	return super.getTrashPriority();
    }
}