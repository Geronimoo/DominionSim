package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class VillainCard extends DomCard {
    public VillainCard() {
      super( DomCardName.Villain);
    }

    public void play() {
      owner.addCoffers(2);
      for (DomPlayer thePlayer : owner.getOpponents()) {
        if (!thePlayer.checkDefense() && thePlayer.getCardsInHand().size()>=5) {
          boolean discarded = false;
          if (thePlayer.isHumanOrPossessedByHuman()) {
              thePlayer.setNeedsToUpdateGUI();
              ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
              ArrayList<DomCard> theChoosFrom = new ArrayList<>();
              for (DomCard theCard:thePlayer.getCardsInHand()) {
                  if (new DomCost(2,0).customCompare(theCard.getCost(owner.getCurrentGame()))<=0)
                      theChoosFrom.add(theCard);
              }
              if (!theChoosFrom.isEmpty())
                thePlayer.discardFromHand(owner.getEngine().getGameFrame().askToSelectOneCardWithDomCard("Choose a card to discard", theChoosFrom, "Mandatory!" ));
              else
                  thePlayer.revealHand();
          } else {
              Collections.sort(thePlayer.getCardsInHand(),SORT_FOR_DISCARDING);
              for (DomCard theCard : thePlayer.getCardsInHand()) {
                  if (new DomCost(2,0).customCompare(theCard.getCost(owner.getCurrentGame()))<=0) {
                      thePlayer.discardFromHand(theCard);
                      discarded=true;
                      break;
                  }
              }
          }
          if (!discarded) {
            if (DomEngine.haveToLog) DomEngine.addToLog(thePlayer + " discards nothing");
            thePlayer.revealHand();
          }
        }
      }
    }
}