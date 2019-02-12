package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class RecruiterCard extends DomCard {
    public RecruiterCard() {
      super( DomCardName.Recruiter);
    }

    public void play() {
      owner.drawCards(2);
      Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
      if (owner.getCardsInHand().isEmpty())
    	  return;
      DomCard theCardToTrash = null;
      if (owner.isHumanOrPossessedByHuman()) {
          owner.setNeedsToUpdateGUI();
          theCardToTrash= owner.getEngine().getGameFrame().askToSelectOneCardWithDomCard("Trash a card", owner.getCardsInHand(), "Mandatory!");
      } else {
          theCardToTrash = owner.getCardsInHand().get(0);
      }
      owner.trash(owner.removeCardFromHand(theCardToTrash));
      if (theCardToTrash.getCoinCost(owner.getCurrentGame())>0)
        owner.addVillagers(theCardToTrash.getCoinCost(owner.getCurrentGame()));
    }
}