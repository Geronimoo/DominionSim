package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class GoatCard extends DomCard {
    public GoatCard() {
      super( DomCardName.Goat);
    }

    public void play() {
      owner.addAvailableCoins(1);
      Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
      if (owner.getCardsInHand().isEmpty())
    	  return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      DomCard theCardToTrash=owner.getCardsInHand().get( 0 );
      if (theCardToTrash.getTrashPriority() < 16 && !owner.removingReducesBuyingPower( theCardToTrash )) {
        owner.trash(owner.removeCardFromHand( theCardToTrash));
      } else {
        if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " trashes nothing");
      }
    }

    private void handleHuman() {
        owner.setNeedsToUpdate();
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Don't trash");
        if (theChosenCard!=null) {
            owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
        } else {
            if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " trashes nothing");
        }
    }
}