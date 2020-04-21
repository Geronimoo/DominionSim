package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class DisplaceCard extends DomCard {
    public DisplaceCard() {
      super( DomCardName.Displace);
    }

    public void play() {
      if (owner.getCardsInHand().isEmpty())
    	return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHumanPlayer();
      } else {
            DomCard theCardToDisplace = owner.findCardToDisplace(this, 2, false);
            if (theCardToDisplace == null) {
                //this is needed when card is played with Throne Room effect or Golem
                Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARDING);
                theCardToDisplace = owner.getCardsInHand().get(0);
            }
            owner.moveToExileMat(owner.removeCardFromHand(theCardToDisplace));
            DomCost theMaxCostOfCardToGain = new DomCost(theCardToDisplace.getCoinCost(owner.getCurrentGame()) + 2, theCardToDisplace.getPotionCost());
            DomCardName theDesiredCard = owner.getDesiredCardWithRestriction(null,theMaxCostOfCardToGain, false, theCardToDisplace.getName());
            if (theDesiredCard == null)
                theDesiredCard = owner.getCurrentGame().getBoard().getBestCardInSupplyFor(owner, null, theMaxCostOfCardToGain, false, null, theCardToDisplace.getName());
            if (theDesiredCard != null)
                owner.gain(theDesiredCard);
        }
    }

    private void handleHumanPlayer() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand())
            theChooseFrom.add(theCard.getName());
        DomCard theCardToDisplace = owner.getCardsFromHand(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to " + this.getName().toString(), theChooseFrom, "Mandatory!")).get(0);
        owner.moveToExileMat(owner.removeCardFromHand(theCardToDisplace));
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theCard!=theCardToDisplace.getName() && theCardToDisplace.getCost(owner.getCurrentGame()).add(new DomCost(2,0)).customCompare(theCard.getCost(owner.getCurrentGame()))>=0
                    && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gain(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain from "+this.getName().toString(), theChooseFrom, "Mandatory!"));
    }

    @Override
    public boolean wantsToBePlayed() {
      return owner.findCardToRemodel(this, 2, false)!=null;
   }
}