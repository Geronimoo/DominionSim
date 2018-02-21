package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class FamineCard extends DomCard {
    public FamineCard() {
      super( DomCardName.Famine);
    }

    public void play() {
      if (owner.getDeckSize()==0)
    	return;
      ArrayList<DomCard> theRevealedCards = owner.revealTopCards(3);
      Collections.sort(theRevealedCards,SORT_FOR_DISCARDING);
      for (DomCard theCard : theRevealedCards){
          if (theCard.hasCardType(DomCardType.Action))
              owner.discard(theCard);
          else
              owner.putOnTopOfDeck(theCard);
      }
      owner.shuffleDeck();
    }
}