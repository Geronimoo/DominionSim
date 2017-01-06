package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class AmbassadorCard extends DomCard {
    public AmbassadorCard () {
      super( DomCardName.Ambassador);
    }

    public void play() {
    	if (owner.getCardsInHand().isEmpty())
          return;
        if (owner.getPlayStrategyFor(this)==DomPlayStrategy.ambassadorWar) {
            handleAmbassadorWar();
            return;
        }
        Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
        DomCard thePreviousTrash = null;
        DomCard theRevealedCard = null;
        int theTrashCount = 0;
        ArrayList<DomCard> setAsideShelters = new ArrayList<DomCard>();
        for (DomCard theCard : owner.getCardsInHand()) {
            if (theCard.hasCardType(DomCardType.Shelter))
                setAsideShelters.add(theCard);
        }
        if (setAsideShelters.size()==owner.getCardsInHand().size())
            return;
        for (DomCard theCard : setAsideShelters) {
            owner.removeCardFromHand(theCard);
        }
        DomCardName theCardToTrash = owner.getCardsInHand().get( 0 ).getName();
        while (theTrashCount<2 && !owner.getCardsFromHand(theCardToTrash).isEmpty()) {
          theRevealedCard = owner.getCardsFromHand(theCardToTrash).get(0);
          if (theRevealedCard.isFromBlackMarket())
              return;
          if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + theRevealedCard );
          if ((owner.getPlayStrategyFor(this)!=DomPlayStrategy.aggressiveTrashing
           && owner.removingReducesBuyingPower(theRevealedCard))
           || owner.getTotalMoneyInDeck()-theRevealedCard.getPotentialCoinValue() < 3
           || theRevealedCard.getTrashPriority()>DomCardName.Copper.getTrashPriority())
            break;
            DomCard theCardToRemove = owner.removeCardFromHand(theRevealedCard);
          owner.returnToSupply(theCardToRemove);
          theTrashCount++;
        }
        attackOpponents( theRevealedCard.getName() );
        owner.addCardsToHand(setAsideShelters);
    }

    private void handleAmbassadorWar() {
        ArrayList<DomCard> setAsideShelters = new ArrayList<DomCard>();
        for (DomCard theCard : owner.getCardsInHand()) {
            if (theCard.hasCardType(DomCardType.Shelter))
                setAsideShelters.add(theCard);
        }
        if (setAsideShelters.size()==owner.getCardsInHand().size())
            return;
        for (DomCard theCard : setAsideShelters) {
            owner.removeCardFromHand(theCard);
        }
        DomCardName theCardToReturn;
        if (!owner.getCardsFromHand(DomCardName.Curse).isEmpty()) {
            theCardToReturn = DomCardName.Curse;
        } else {
            if (!owner.getCardsFromHand(DomCardName.Estate).isEmpty() && owner.getCardsFromHand(DomCardName.Estate).size()>1 && !owner.wants(DomCardName.Estate)) {
                theCardToReturn = DomCardName.Estate;
            } else {
                if (!owner.getCardsFromHand(DomCardName.Copper).isEmpty() && owner.getCardsFromHand(DomCardName.Copper).size()>1 && owner.getTotalMoneyInDeck()>4) {
                    theCardToReturn = DomCardName.Copper;
                } else {
                    Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
                    theCardToReturn = owner.getCardsInHand().get(0).getName();
                }
            }
        }
        int theTrashCount=0;
        DomCard theRevealedCard;
        while (theTrashCount<2 && !owner.getCardsFromHand(theCardToReturn).isEmpty()) {
            theRevealedCard = owner.getCardsFromHand(theCardToReturn).get(0);
            if (theRevealedCard.isFromBlackMarket())
                return;
            if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + theRevealedCard );
            if (owner.getTotalMoneyInDeck()-theRevealedCard.getPotentialCoinValue() < 3
                || theRevealedCard.getTrashPriority()>DomCardName.Copper.getTrashPriority())
               break;
            if (theCardToReturn!=DomCardName.Curse || owner.getCurrentGame().countInSupply(DomCardName.Curse)==0 || owner.countInDeck(DomCardName.Curse)>1) {
                if (owner.getActionsLeft()==0 || owner.getNextActionToPlay()==null || owner.getNextActionToPlay().getName()!=DomCardName.Ambassador || (!owner.getCardsFromHand(theCardToReturn).isEmpty() && owner.getCardsFromHand(theCardToReturn).size()>0)) {
                    DomCard theCardToRemove = owner.removeCardFromHand(theRevealedCard);
                    owner.returnToSupply(theCardToRemove);
                }
            }

            theTrashCount++;
        }
        attackOpponents( theCardToReturn );
        owner.addCardsToHand(setAsideShelters);
    }

    private void attackOpponents( DomCardName aCardName ) {
      for (DomPlayer thePlayer : owner.getOpponents()) {
         if (thePlayer.checkDefense()) 
        	 continue;
         DomCard theCard = owner.getCurrentGame().takeFromSupply( aCardName);
         if (theCard!=null) {
           thePlayer.gain(theCard);
         }
      }
    }

    public boolean wantsToBePlayed() {
      for (DomCard theCard : owner.getCardsInHand()) {
        if (theCard!=this && theCard.getTrashPriority()<16 && !theCard.isFromBlackMarket())
          return true;
      }
      return false;
   }
    @Override
    public int getTrashPriority() {
    	if (owner==null)
    		return super.getTrashPriority();

    	int theCount=0;
    	for (DomCardName card : owner.getDeck().keySet()){
            //avoid endless loop when both Temple and Amb in deck
            if (card==DomCardName.Ambassador || card==DomCardName.Temple)
    			continue;
    		if (card.getTrashPriority(owner)<16)
    			theCount+=owner.countInDeck(card);
    	}
    	if (theCount<3 && owner.countInDeck(DomCardName.Curse)==0)
    		return 14;
    	return super.getTrashPriority();
    }
}