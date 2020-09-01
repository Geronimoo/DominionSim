package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class GearCard extends DomCard {
    private ArrayList<DomCard> mySetAsideCards=new ArrayList<DomCard>();

    public GearCard() {
      super(DomCardName.Gear);
    }

    public void play() {
      owner.drawCards(2);
      if (owner.getCardsInHand().isEmpty())
    	  return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      if (owner.wants((DomCardName.Alliance)) && owner.getTotalMoneyInDeck()>18 && owner.count(DomCardName.Province)==0 && owner.getTotalPotentialCurrency().getCoins()<10) {
          Collections.sort(owner.getCardsInHand(), DomCard.SORT_FOR_DISCARD_FROM_HAND);
          mySetAsideCards.add(owner.getCardsInHand().remove(owner.getCardsInHand().size()-1));
          mySetAsideCards.add(owner.getCardsInHand().remove(owner.getCardsInHand().size()-1));
          if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has set aside " + mySetAsideCards);
          return;
      }

      Collections.sort(owner.getCardsInHand(), DomCard.SORT_FOR_DISCARD_FROM_HAND);
      if (owner.getCardsInHand().get(0).hasCardType(DomCardType.Action) && owner.getActionsAndVillagersLeft()==0) {
          mySetAsideCards.add(owner.getCardsInHand().remove(0));
          if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has set aside " + mySetAsideCards);
      }
      if (owner.getCardsInHand().isEmpty())
          return;
      if (owner.getCardsInHand().get(0).hasCardType(DomCardType.Action) && owner.getActionsAndVillagersLeft()==0) {
          mySetAsideCards.add(owner.getCardsInHand().remove(0));
          if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has set aside " + mySetAsideCards);
      }
        if (mySetAsideCards.size()==2)
            return;
      ArrayList<DomCard> theTreasures = owner.getCardsFromHand(DomCardType.Treasure);
      Collections.sort(theTreasures,SORT_FOR_DISCARDING);
      if (theTreasures.isEmpty()) {
          checkForOtherJunk();
          return;
      }
      int i = theTreasures.size() - 1;
      while ((owner.removingReducesBuyingPower(theTreasures.get(i)) || theTreasures.get(i).getName()==DomCardName.Horn_of_Plenty) && i>0)
          i--;
      if (owner.removingReducesBuyingPower(theTreasures.get(i)) || theTreasures.get(i).getName()==DomCardName.Horn_of_Plenty) {
          checkForOtherJunk();
          return;
      }
      mySetAsideCards.add(owner.removeCardFromHand(theTreasures.get(i)));
      theTreasures.remove(i);
      if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has set aside " + mySetAsideCards);
      if (mySetAsideCards.size()==2)
          return;
      if (theTreasures.isEmpty()) {
          checkForOtherJunk();
          return;
      }
      i = theTreasures.size() - 1;
      while (owner.removingReducesBuyingPower(theTreasures.get(i)) && i>0)
          i--;
      if (owner.removingReducesBuyingPower(theTreasures.get(i))) {
          checkForOtherJunk();
          return;
      }
      mySetAsideCards.add(owner.removeCardFromHand(theTreasures.get(i)));
      theTreasures.remove(i);
      if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has set aside " + mySetAsideCards);

    }

    private void handleHuman() {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("Set aside cards", owner.getCardsInHand(), theChosenCards, 0);
        while (theChosenCards.size()>2)
            owner.getEngine().getGameFrame().askToSelectCards("Set aside cards", owner.getCardsInHand(), theChosenCards, 0);
        for (DomCard theCardName: theChosenCards) {
            for (DomCard theCard:owner.getCardsInHand()) {
                if (theCard.getName()==theCardName.getName()) {
                    mySetAsideCards.add(owner.removeCardFromHand(theCard));
                    break;
                }
            }
        }
        if (!mySetAsideCards.isEmpty())
            if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has set aside " + mySetAsideCards);
    }

    private void checkForOtherJunk() {
        if (owner.getCardsInHand().isEmpty())
            return;
        if (owner.getCardsInHand().get(0).getDiscardPriority(owner.getActionsAndVillagersLeft())<DomCardName.Copper.getDiscardPriority(1)) {
                mySetAsideCards.add(owner.getCardsInHand().remove(0));
                if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has set aside " + mySetAsideCards);
        }
        if (owner.getCardsInHand().isEmpty() || mySetAsideCards.size()==2)
            return;
        if (owner.getCardsInHand().get(0).getDiscardPriority(owner.getActionsAndVillagersLeft())<DomCardName.Copper.getDiscardPriority(1)) {
                mySetAsideCards.add(owner.getCardsInHand().remove(0));
                if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has set aside " + mySetAsideCards);
        }
    }

    public void resolveDuration() {
      owner.getCardsInHand().addAll(mySetAsideCards);
      if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " adds " + mySetAsideCards +" to hand");
      mySetAsideCards.clear();
    }

    @Override
    public void cleanVariablesFromPreviousGames() {
        mySetAsideCards.clear();
    }

    @Override
    public boolean mustStayInPlay() {
        return !mySetAsideCards.isEmpty();
    }

    @Override
    public boolean discardAtCleanUp() {
        return mySetAsideCards.isEmpty();
    }
}