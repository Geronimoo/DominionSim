package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;

import java.util.ArrayList;
import java.util.Collections;

public class MarchlandCard extends DomCard {
    public MarchlandCard() {
      super( DomCardName.Marchland);
    }

    public void doWhenGained() {
      owner.addAvailableBuys(1);
      if (owner.isHumanOrPossessedByHuman()) {
          ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
          owner.getEngine().getGameFrame().askToSelectCards("<html>Discard for +$1</html>" , owner.getCardsInHand(), theChosenCards, 0);
          for (DomCard theCard : theChosenCards) {
             owner.discardFromHand(theCard.getName());
             owner.addAvailableCoins(1);
          }
      } else {
          int cardsInHandSize = owner.getCardsInHand().size();
          owner.doForcedDiscard(cardsInHandSize, false);
          owner.addAvailableCoins(cardsInHandSize);
      }
    }

	private void playNormal() {
      Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
      DomCard theCardToDiscard = null;
      while (!owner.getCardsInHand().isEmpty()){
	    theCardToDiscard = owner.getCardsInHand().get(0);
	    if (theCardToDiscard.getDiscardPriority(owner.getActionsAndVillagersLeft()) >= 20)
	      return;
        owner.discardFromHand(theCardToDiscard);
	    owner.addAvailableCoins( 1 );
	  } 
	}

	private void handleTactician() {
	  //first set aside Tactician and 1 card to trigger the Tactician
      DomCard theTactician = owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Tactician).get(0));
      Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
      DomCard theCardToTriggerTactician = owner.removeCardFromHand(owner.getCardsInHand().get(0));
      //then discard everything
	  while (!owner.getCardsInHand().isEmpty()){
        owner.discardFromHand(owner.getCardsInHand().get(0));
	    owner.addAvailableCoins( 1 );
	  }
	  //then return theTactician and 1 card to the hand
	  owner.getCardsInHand().add(theTactician);
	  owner.getCardsInHand().add(theCardToTriggerTactician);
	}
}