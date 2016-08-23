package be.aga.dominionSimulator.cards;



import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class AdventurerCard extends DomCard {
    public AdventurerCard () {
      super( DomCardName.Adventurer);
    }

    public void play() {
      ArrayList< DomCard > theCards = owner.revealUntilType(DomCardType.Treasure);
      theCards.addAll(owner.revealUntilType(DomCardType.Treasure));
      for (DomCard theCard:theCards) {
    	if (theCard.hasCardType(DomCardType.Treasure)){
           owner.putInHand(theCard);
    	} else {
    	   owner.discard(theCard);
    	}
      }
    }
}