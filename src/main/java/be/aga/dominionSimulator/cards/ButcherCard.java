package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class ButcherCard extends DomCard {
    public ButcherCard() {
      super( DomCardName.Butcher);
    }

    public void play() {
      owner.addCoinTokens(2);
      if (owner.getCardsInHand().isEmpty())
    	return;
      DomCard theCardToTrash = findCardToTrash();
      if (theCardToTrash==null) {
        if (DomEngine.haveToLog) DomEngine.addToLog( this + " trashes nothing." );
        return;
      }
      owner.trash(owner.removeCardFromHand( theCardToTrash));
      DomCost theMaxCostOfCardToGain = new DomCost( theCardToTrash.getCoinCost(owner.getCurrentGame()) + owner.getCoinTokens(), theCardToTrash.getPotionCost());
	  DomCardName theDesiredCard = owner.getDesiredCard(theMaxCostOfCardToGain, false);
      if (theDesiredCard==null)
    	theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theMaxCostOfCardToGain);
      if (theDesiredCard!=null) {
          int theCoinTokens = theDesiredCard.getCoinCost(owner.getCurrentGame()) - theCardToTrash.getCoinCost(owner.getCurrentGame());
          if (theCoinTokens>0)
              owner.spendCoinTokens(theCoinTokens);
          owner.gain(theDesiredCard);
      }
    }
    
    private DomCard findCardToTrash() {
        if (!owner.getCardsFromHand(DomCardName.Curse).isEmpty())
            return owner.getCardsFromHand(DomCardName.Curse).get(0);

        if (owner.stillInEarlyGame()) {
            if (!owner.getCardsFromHand(DomCardName.Estate).isEmpty())
                return owner.getCardsFromHand(DomCardName.Estate).get(0);
        } else {
            if (owner.isGoingToBuyTopCardInBuyRules(owner.getTotalAvailableCurrency()))
                return null;
            DomCard theBestCardToTrash = owner.getCardsInHand().get(0);
            DomCost theMaxCostOfCardToGain = new DomCost(theBestCardToTrash.getCoinCost(owner.getCurrentGame()) + owner.getCoinTokens(), theBestCardToTrash.getPotionCost());
            DomCardName theBestCardToGain = owner.getDesiredCard(theMaxCostOfCardToGain, false);
            for (DomCard card : owner.getCardsInHand()) {
                theMaxCostOfCardToGain = new DomCost(card.getCoinCost(owner.getCurrentGame()) + owner.getCoinTokens(), card.getPotionCost());
                DomCardName theRemodelGainCard = owner.getDesiredCard(theMaxCostOfCardToGain, false);
                if (theRemodelGainCard==null)
                    theRemodelGainCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theMaxCostOfCardToGain);
                if (theBestCardToGain==null ||
                        (theRemodelGainCard.getTrashPriority(owner) > theBestCardToGain.getTrashPriority(owner)
                        && theRemodelGainCard.getTrashPriority(owner) >= card.getTrashPriority()) ) {
                    theBestCardToTrash = card;
                    theBestCardToGain = theRemodelGainCard;
                }
            }
            return theBestCardToTrash;
        }
        return null;
   }
}