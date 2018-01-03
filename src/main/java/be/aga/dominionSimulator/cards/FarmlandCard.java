package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

public class FarmlandCard extends DomCard {
    public FarmlandCard () {
      super( DomCardName.Farmland);
    }
    
    public void remodelSomething() {
      if (owner.getCardsInHand().isEmpty())
    	return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
      DomCardName theDesiredCard =null;
      for (DomCard card : owner.getCardsInHand()){
    	  theDesiredCard =owner.getDesiredCard(card.getCost(owner.getCurrentGame()).add(new DomCost(2, 0)), true); 
    	  if (theDesiredCard!=null){
    		  owner.trash(owner.removeCardFromHand(card));
    		  owner.gain(theDesiredCard);
    		  return;
    	  }
      }
      //we arrive here, so nothing was trashed because no valid target
      DomCard theCard = owner.getCardsInHand().get(0);
      DomCost theCost = theCard.getCost(owner.getCurrentGame()).add(new DomCost(2, 0));
      owner.trash(owner.removeCardFromHand( theCard));
      theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theCost, true);
      if (theDesiredCard!=null)
    	  owner.gain(theDesiredCard);
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand())
            theChooseFrom.add(theCard.getName());
        DomCard theCardToRemodel = owner.getCardsFromHand(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to " + this.getName().toString(), theChooseFrom, "Mandatory!")).get(0);
        owner.trash(owner.removeCardFromHand(theCardToRemodel));
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theCardToRemodel.getCost(owner.getCurrentGame()).add(new DomCost(2,0)).customCompare(theCard.getCost(owner.getCurrentGame()))==0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gain(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain from "+this.getName().toString(), theChooseFrom, "Mandatory!"));
    }

    @Override
    public int getTrashPriority() {
    	return 16;
//      if (owner!=null && owner.wantsToGainOrKeep(DomCardName.Farmland))
//          return 39;
//      return super.getTrashPriority();
    }
}