package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class Pearl_DiverCard extends DomCard {
    public Pearl_DiverCard () {
      super( DomCardName.Pearl_Diver);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      DomCard theBottomCard = owner.getBottomCardFromDeck();
      if (theBottomCard==null) {
    	  if (DomEngine.haveToLog) DomEngine.addToLog("but deck is empty");
    	  return;
      }
      if (DomEngine.haveToLog) DomEngine.addToLog(owner + " looks at the bottom card");
      if (theBottomCard.getDiscardPriority(owner.actionsLeft)>=16){
    	  owner.putOnTopOfDeck(theBottomCard);
      } else {
    	  owner.putOnBottomOfDeck(theBottomCard);
      }
    }
}