package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class Zombie_ApprenticeCard extends DomCard {
    public Zombie_ApprenticeCard() {
      super( DomCardName.Zombie_Apprentice);
    }

    public void play() {
      if (owner.getCardsFromHand(DomCardType.Action).isEmpty())
    	  return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      ArrayList<DomCard> theActions = owner.getCardsFromHand(DomCardType.Action);
      Collections.sort( theActions , SORT_FOR_TRASHING);
      DomCard theCardToTrash = owner.getCardsInHand().get(0);
      for (DomCard theCard : owner.getCardsInHand()) {
    	  if (theCard.getName()!=DomCardName.Market_Square){
             theCardToTrash=theCard;
             break;
    	  }
      }
      owner.trash(owner.removeCardFromHand( theCardToTrash));
      owner.drawCards(3);
      owner.addActions( 1 );
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsFromHand(DomCardType.Action)) {
            theChooseFrom.add(theCard.getName());
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Don't trash!");
        if (theChosenCard != null) {
            DomCard theCard = owner.getCardsFromHand(theChosenCard).get(0);
            owner.trash(owner.removeCardFromHand(theCard));
            owner.drawCards(3);
            owner.addActions(1);
        }
    }
}