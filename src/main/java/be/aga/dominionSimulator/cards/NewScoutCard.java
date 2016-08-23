package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class NewScoutCard extends DomCard {
    public NewScoutCard() {
      super( DomCardName.NewScout);
    }

    public void play() {
      owner.drawCards(3);
      ArrayList< DomCard > theCards = owner.revealTopCards(4);
      Collections.sort(theCards,SORT_FOR_DISCARDING);
      for (DomCard theCard:theCards) {
    	if (theCard.hasCardType(DomCardType.Victory)){
           owner.putInHand(theCard);
    	} else {
    	   owner.putOnTopOfDeck(theCard);
    	}
      }
    }
}