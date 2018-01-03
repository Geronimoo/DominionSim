package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class PillageCard extends DomCard {
    public PillageCard() {
      super( DomCardName.Pillage);
    }

    public void play() {
      for (DomPlayer thePlayer : owner.getOpponents()) {
          if (!thePlayer.checkDefense() && thePlayer.getCardsInHand().size() >= 5) {
              if (owner.isHumanOrPossessedByHuman()) {
                  ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
                  for (DomCard theCard : thePlayer.getCardsInHand()) {
                      theChooseFrom.add(theCard.getName());
                  }
                  thePlayer.discardFromHand(owner.getEngine().getGameFrame().askToSelectOneCard("Make player discard", theChooseFrom, "Mandatory!"));
              } else {
                  ArrayList<DomCard> cardsInHand = thePlayer.getCardsInHand();
                  Collections.sort(cardsInHand, SORT_FOR_DISCARDING);
                  thePlayer.discard( cardsInHand.remove( cardsInHand.size()-1 ) );
              }
          }
      }
      owner.gain(DomCardName.Spoils);
      owner.gain(DomCardName.Spoils);
      if (owner.getCardsInPlay().contains(this))
        owner.trash(owner.removeCardFromPlay(this));
    }
}