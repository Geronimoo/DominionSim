package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Set;

public class CourtyardCard extends DomCard {
    public CourtyardCard () {
      super( DomCardName.Courtyard);
    }

    public void play() {
    	owner.drawCards(3);
    	if (owner.getCardsInHand().isEmpty()) {
  	      if (DomEngine.haveToLog) 
            DomEngine.addToLog( owner + "'s hand is empty, so returns nothing");
          return;
    	}
    	if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
        } else {
            owner.putCardFromHandOnTop();
        }
    }

    private void handleHuman() {
        Set<DomCardName> uniqueCards = owner.getUniqueCardNamesInHand();
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        theChooseFrom.clear();
        theChooseFrom.addAll(uniqueCards);
        owner.setNeedsToUpdateGUI();
        DomCard theChosenCard = owner.getCardsFromHand(owner.getEngine().getGameFrame().askToSelectOneCard("Put back a card for " + this.getName().toString(), theChooseFrom, "Mandatory!")).get(0);
        owner.putOnTopOfDeck(owner.removeCardFromHand(theChosenCard));
    }

    @Override
    public boolean wantsToBePlayed() {
      return !owner.isDeckEmpty();
    }

    @Override
    public int getPlayPriority() {
        return owner.getActionsAndVillagersLeft()>1 ? 10 : super.getPlayPriority();
    }
}