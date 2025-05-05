package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class ButcherCard extends DomCard {
    public ButcherCard() {
      super( DomCardName.Butcher);
    }

    public void play() {
      owner.addCoffers(2);
      if (owner.getCardsInHand().isEmpty())
    	return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHumanPlayer();
          return;
      }
      DomCard theCardToTrash = findCardToTrash();
      if (theCardToTrash==null) {
        if (DomEngine.haveToLog) DomEngine.addToLog( this + " trashes nothing." );
        return;
      }
      owner.trash(owner.removeCardFromHand( theCardToTrash));
      DomCost theMaxCostOfCardToGain = new DomCost( theCardToTrash.getCoinCost(owner.getCurrentGame()) + owner.getCoffers(), theCardToTrash.getPotionCost());
	  DomCardName theDesiredCard = owner.getDesiredCard(theMaxCostOfCardToGain, false);
      if (theDesiredCard==null)
    	theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theMaxCostOfCardToGain);
      if (theDesiredCard!=null) {
          int theCoffersToSpend = theDesiredCard.getCoinCost(owner) - theCardToTrash.getCoinCost(owner.getCurrentGame());
          if (theCoffersToSpend>0)
              owner.spendCoffers(theCoffersToSpend);
          owner.gain(theDesiredCard);
      }
    }

    private void handleHumanPlayer() {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand())
            theChooseFrom.add(theCard.getName());
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select card to " + this.getName().toString(), theChooseFrom, "Don't trash anything!");
        if (theChosenCard==null)
            return;
        DomCard theCardToButcher = owner.getCardsFromHand(theChosenCard).get(0);
        owner.trash(owner.removeCardFromHand(theCardToButcher));
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theCardToButcher.getCost(owner.getCurrentGame()).add(new DomCost(owner.getCoffers(),0)).customCompare(theCard.getCost(owner.getCurrentGame()))>=0
                    && owner.getCurrentGame().countInSupply(theCard)>0 )
//                    && theCard.getCost(owner.getCurrentGame()).getDebt()==theCardToButcher.getCost(owner.getCurrentGame()).getDebt())
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain from " + this.getName().toString(), theChooseFrom, "Mandatory!");

        int theCoffersToSpend = theChosenCard.getCoinCost(owner) - theCardToButcher.getCoinCost(owner.getCurrentGame());
        if (theCoffersToSpend>0)
          owner.spendCoffers(theCoffersToSpend);
        owner.gain(theChosenCard);
    }

    private DomCard findCardToTrash() {
        if (!owner.getCardsFromHand(DomCardName.Curse).isEmpty())
            return owner.getCardsFromHand(DomCardName.Curse).get(0);
        if (!owner.getCardsFromHand(DomCardName.Rats).isEmpty())
            return owner.getCardsFromHand(DomCardName.Rats).get(0);

        if (owner.stillInEarlyGame() && !owner.getCardsFromHand(DomCardName.Estate).isEmpty()) {
            return owner.getCardsFromHand(DomCardName.Estate).get(0);
        } else {
            if (owner.isGoingToBuyTopCardInBuyRules(owner.getTotalAvailableCurrency()))
                return null;
            Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
            DomCard theBestCardToTrash = owner.getCardsInHand().get(0);
            DomCost theMaxCostOfCardToGain = new DomCost(theBestCardToTrash.getCoinCost(owner.getCurrentGame()) + owner.getCoffers(), theBestCardToTrash.getPotionCost());
            DomCardName theBestCardToGain = owner.getDesiredCard(theMaxCostOfCardToGain, false);
            for (DomCard card : owner.getCardsInHand()) {
                if (card.getName()==DomCardName.Copper || card.getName()==DomCardName.Province)
                    continue;
                theMaxCostOfCardToGain = new DomCost(card.getCoinCost(owner.getCurrentGame()) + owner.getCoffers(), card.getPotionCost());
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
            return theBestCardToTrash.getName()==DomCardName.Copper?null:theBestCardToTrash;
        }
   }

    @Override
    public int getPlayPriority() {
        if (owner.getCardsFromHand(DomCardName.Rats).size()==1)
            return 12;
        return super.getPlayPriority();
    }
}