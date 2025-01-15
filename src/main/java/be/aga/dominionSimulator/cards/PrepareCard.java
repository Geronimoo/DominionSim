package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class PrepareCard extends DomCard {
    public PrepareCard() {
      super( DomCardName.Prepare);
    }

    public void play() {
      if (owner.getCardsInHand().isEmpty())
    	  return;
      owner.addPreparedCards(owner.getCardsInHand());
      if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " prepares all cards in hand: " + owner.getPreparedCards() );
      owner.clearCardsInHand();
    }
}