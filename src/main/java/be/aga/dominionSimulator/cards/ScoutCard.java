package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class ScoutCard extends DomCard {
    public ScoutCard () {
      super( DomCardName.Scout);
    }

    public void play() {
      owner.addActions(1);
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