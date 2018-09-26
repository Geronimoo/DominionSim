package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class TorturerCard extends DomCard {
    public TorturerCard () {
      super( DomCardName.Torturer);
    }

    public void play() {
      owner.drawCards(3);
      for (DomPlayer thePlayer : owner.getOpponents()) {
          if (thePlayer.checkDefense()) 
         	continue;
          if (thePlayer.isHumanOrPossessedByHuman()) {
              handleHuman(thePlayer);
              return;
          }
	      boolean trashCardPresent=false;
	      if (owner.getCurrentGame().countInSupply(DomCardName.Curse )==0){
	         if (DomEngine.haveToLog) 
	           DomEngine.addToLog( thePlayer + " chooses to gain a Curse because the Curse pile is empty");
	         continue;
	      }
	      ArrayList<DomCard> cardsInHand = thePlayer.getCardsInHand();
	      for (DomCard theCard : cardsInHand){
	    	if (theCard.hasCardType(DomCardType.Trasher)){
	    	   trashCardPresent = true;
	    	   break;
	    	}
	      }
	      if (trashCardPresent)
	    	thePlayer.gainInHand(DomCardName.Curse);
	      else
            if (isHandTooStrongToDiscard(thePlayer))
                thePlayer.gainInHand(DomCardName.Curse);
            else
                thePlayer.doForcedDiscard(2, false);
      }
    }

    private void handleHuman(DomPlayer thePlayer) {
        thePlayer.setNeedsToUpdateGUI();
        ArrayList<String> theOptions = new ArrayList<String>();
        theOptions.add("Discard 2 cards");
        if (owner.getCurrentGame().countInSupply(DomCardName.Curse) == 0)
            theOptions.add("Don't Discard");
        else
            theOptions.add("Gain a Curse in hand");
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Torturer attacks", theOptions, "Mandatory!");
        if (theChoice == 0) {
            thePlayer.doForcedDiscard(2, false);
        } else {
            thePlayer.gainInHand(DomCardName.Curse);
        }
    }

    private boolean isHandTooStrongToDiscard(DomPlayer aPlayer) {
        if (!aPlayer.isGoingToBuyTopCardInBuyRules(aPlayer.getTotalPotentialCurrency()))
           return false;
        if (aPlayer.getCardsInHand().size()<2)
            return false;
        Collections.sort(aPlayer.getCardsInHand(),SORT_FOR_DISCARDING);
        ArrayList<DomCard> theRemovedCards = new ArrayList<DomCard>();
        theRemovedCards.add(aPlayer.getCardsInHand().remove(0));
        theRemovedCards.add(aPlayer.getCardsInHand().remove(0));
        if (aPlayer.isGoingToBuyTopCardInBuyRules(aPlayer.getTotalPotentialCurrency())) {
            aPlayer.getCardsInHand().addAll(theRemovedCards);
            return false;
        } else {
            aPlayer.getCardsInHand().addAll(theRemovedCards);
            return true;
        }
    }

    @Override
    public int getPlayPriority() {
        return owner.getActionsLeft()>1 ? 6 : super.getPlayPriority();
    }
}