package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class RaiderCard extends DomCard {
    public RaiderCard() {
      super( DomCardName.Raider);
    }

    @Override
    public void play() {
        for (DomPlayer theOpp : owner.getOpponents())
            if (!theOpp.checkDefense()) {
              if (theOpp.isHumanOrPossessedByHuman()) {
                  ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
                  for (DomCard theCard : owner.getCardsInPlay()) {
                    if (!theOpp.getCardsFromHand(theCard.getName()).isEmpty())
                      theChooseFrom.add(theCard.getName());
                  }
                  if (theChooseFrom.isEmpty())
                      continue;
                  DomCardName theChosenCard = theOpp.getEngine().getGameFrame().askToSelectOneCard("Discard", theChooseFrom, "Mandatory!");
                  theOpp.discardFromHand(theOpp.getCardsFromHand(theChosenCard).get(0));
              } else{
                  Collections.sort(theOpp.getCardsInHand(), SORT_FOR_DISCARDING);
                  for (DomCard theCard: theOpp.getCardsInHand()){
                      if (!owner.getCardsFromPlay(theCard.getName()).isEmpty()) {
                          theOpp.discardFromHand(theCard);
                          return;
                      }
                  }
              }
            }
    }

    public void resolveDuration() {
      owner.addAvailableCoins(3);
    }
}