package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class NavigatorCard extends DomCard {
    public NavigatorCard () {
      super( DomCardName.Navigator);
    }

    public void play() {
      owner.addAvailableCoins(2);
      ArrayList<DomCard> theCards = owner.revealTopCards(5);
      if (theCards.isEmpty())
          return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman(theCards);
          return;
      }
      int theTotal=0;
      for (DomCard card : theCards){
    	theTotal+=card.getDiscardPriority(1);
    	if (card.getName()==DomCardName.Tunnel){
    		owner.discard(theCards);
    		return;
    	}
      }
      if (theTotal<80) {
        owner.discard(theCards);
      } else {
    	  Collections.sort(theCards,SORT_FOR_DISCARD_FROM_HAND);
    	  for (DomCard theCard : theCards) {
    	    owner.putOnTopOfDeck(theCard);
    	  }
      }
    }

    private void handleHuman(ArrayList<DomCard> theCards) {
        StringBuilder theStr = new StringBuilder();
        String thePrefix = "";
        for (DomCard theCard : theCards) {
            theStr.append(thePrefix).append(theCard.getName().toHTML());
            thePrefix=", ";
        }
        if (owner.getEngine().getGameFrame().askPlayer("<html>Discard " + theStr +"</html>", "Resolving " + this.getName().toString())) {
            owner.discard(theCards);
        } else {
            ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
            owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>" , theCards, theChosenCards, theCards.size());
            for (int i=theChosenCards.size()-1;i>=0;i--) {
                for (DomCard theCard : theCards) {
                    if (theChosenCards.get(i).getName()==theCard.getName()) {
                        owner.putOnTopOfDeck(theCard);
                        theCards.remove(theCard);
                        break;
                    }
                }
            }
        }
    }
}