package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class SwindlerCard extends DomCard {
    public SwindlerCard () {
      super( DomCardName.Swindler);
    }

    public void play() {
      owner.addAvailableCoins(2);
      for (DomPlayer thePlayer : owner.getOpponents()) {
          if (thePlayer.checkDefense()) 
        	continue;
          ArrayList< DomCard > theCards = thePlayer.revealTopCards(1);
          if (theCards.isEmpty())
        	  continue;
          DomCardName theNewCard = owner.getCurrentGame().getCardForSwindler(thePlayer, theCards.get(0).getCost(owner.getCurrentGame()));
          thePlayer.trash(theCards.get(0));
          if (theNewCard!=null)
            thePlayer.gain(theNewCard);
          else
        	if (DomEngine.haveToLog) DomEngine.addToLog(thePlayer + " gains nothing");
      }
    }
}