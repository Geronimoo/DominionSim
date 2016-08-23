package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Scrying_PoolCard extends DomCard {
    public Scrying_PoolCard () {
      super( DomCardName.Scrying_Pool);
    }

    public void play() {
      owner.addActions(1);
      ArrayList< DomCard > theCards = owner.revealTopCards(1);
      if (!theCards.isEmpty()){
    	  DomCard theTopCard = theCards.get(0);
    	  if (theTopCard.hasCardType(DomCardType.Action)||theTopCard.getDiscardPriority(1)>29){
    		  owner.putOnTopOfDeck(theTopCard);
    	  }else{
    		  owner.discard(theTopCard);
    	  }
      }
      for (DomPlayer player : owner.getOpponents()){
        theCards = player.revealTopCards(1);
        if (!theCards.isEmpty()){
          DomCard theTopCard = theCards.get(0);
          if (theTopCard.getDiscardPriority(1)<16){
            player.putOnTopOfDeck(theTopCard);
          }else{
        	player.discard(theTopCard);
          }
        }
      }
      do {
    	  theCards = owner.revealTopCards(1);
          if (!theCards.isEmpty()){
    	    owner.putInHand(theCards.get(0));
          }
      } while (!theCards.isEmpty() && theCards.get(0).hasCardType(DomCardType.Action));
    }
}