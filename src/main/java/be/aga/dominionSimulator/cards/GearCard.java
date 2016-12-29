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
      Collections.sort(owner.getCardsInHand(), DomCard.SORT_FOR_DISCARD_FROM_HAND);
      if (owner.getCardsInHand().get(0).hasCardType(DomCardType.Action) && owner.actionsLeft==0) {
          mySetAsideCards.add(owner.getCardsInHand().remove(0));
          if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has set aside " + mySetAsideCards);
      }
      if (owner.getCardsInHand().isEmpty())
          return;
      if (owner.getCardsInHand().get(0).hasCardType(DomCardType.Action) && owner.actionsLeft==0) {
          mySetAsideCards.add(owner.getCardsInHand().remove(0));
          if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has set aside " + mySetAsideCards);
      }
      ArrayList<DomCard> theTreasures = owner.getCardsFromHand(DomCardType.Treasure);
      Collections.sort(theTreasures,SORT_FOR_DISCARDING);
      if (mySetAsideCards.size()==2)
          return;
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

    private void checkForOtherJunk() {
        if (owner.getCardsInHand().isEmpty())
            return;
        if (owner.getCardsInHand().get(0).getDiscardPriority(owner.getActionsLeft())<DomCardName.Copper.getDiscardPriority(1)) {
            mySetAsideCards.add(owner.getCardsInHand().remove(0));
            if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has set aside " + mySetAsideCards);
        }
        if (owner.getCardsInHand().isEmpty() || mySetAsideCards.size()==2)
            return;
        if (owner.getCardsInHand().get(0).getDiscardPriority(owner.getActionsLeft())<DomCardName.Copper.getDiscardPriority(1)) {
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
}