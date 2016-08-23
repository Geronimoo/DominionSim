package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class Wandering_MinstrelCard extends DomCard {
    public Wandering_MinstrelCard() {
      super( DomCardName.Wandering_Minstrel);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(1);
      if (owner.getDeckSize()==0)
    	return;
      ArrayList<DomCard> theRevealedCards = owner.revealTopCards(3);
      Collections.sort(theRevealedCards,SORT_FOR_DISCARDING);
      for (DomCard theCard : theRevealedCards){
          if (theCard.hasCardType(DomCardType.Action))
              owner.putOnTopOfDeck(theCard);
          else
              owner.discard(theCard);
      }
    }
}