package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class Sea_ChartCard extends DomCard {
    public Sea_ChartCard() {
      super( DomCardName.Sea_Chart);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      if (owner.getDeckAndDiscardSize()==0)
    	return;
      DomCardName theChoice = null;
      DomCard theRevealedCard = owner.revealTopCards(1).get(0);
      owner.putOnTopOfDeck(theRevealedCard);
      if (!owner.getCardsFromPlay(theRevealedCard.getName()).isEmpty()) {
          if (DomEngine.haveToLog) DomEngine.addToLog(this.getName().toHTML() + " hits! ");
          owner.drawCards(1);
      }
    }

}