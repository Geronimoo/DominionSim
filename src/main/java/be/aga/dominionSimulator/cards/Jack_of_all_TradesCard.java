package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Jack_of_all_TradesCard extends DrawUntilXCardsCard {
    public Jack_of_all_TradesCard () {
      super( DomCardName.Jack_of_all_Trades);
    }

    public void play() {
      gainSilver();
      spyOnYourself();
      drawUntil5CardsInHand();
      maybeTrashNonTreasureFromHand();
    }

	private void maybeTrashNonTreasureFromHand() {
		if (owner.getCardsInHand().isEmpty())
			  return;
		  Collections.sort( owner.getCardsInHand(), SORT_FOR_TRASHING);
		  DomCard theCardToTrash = findCardToTrash();
		  if (theCardToTrash==null){
			if (DomEngine.haveToLog) DomEngine.addToLog(owner + " trashes nothing");
		  } else{
			owner.trash(owner.removeCardFromHand(theCardToTrash));
		  }
	}

	private DomCard findCardToTrash() {
	  DomCard theCardToTrash = null;
	  for (DomCard card : owner.getCardsInHand()){
		  if (card.getTrashPriority()<16 && !card.hasCardType(DomCardType.Treasure)){
			  theCardToTrash=card;
			  break;
		  }
	  }
	  return theCardToTrash;
	}

	private void drawUntil5CardsInHand() {
		owner.drawCards( 5 - owner.getCardsInHand().size() );
	}

	private void gainSilver() {
		if (owner.getCurrentGame().countInSupply(DomCardName.Silver)>0)
			  owner.gain(DomCardName.Silver);
	}

	private void spyOnYourself() {
	  ArrayList<DomCard> theRevealedCard = owner.revealTopCards(1);
	  if (theRevealedCard.isEmpty())
		  return;
	  DomCard theCard = theRevealedCard.get(0);
	  if (theCard.getDiscardPriority(owner.getActionsLeft())>=16
              || (owner.getCardsInHand().size()>=5 && theCard.getDiscardPriority(1)>=16)
			     || (findCardToTrash()==null 
				         && theCard.getTrashPriority()<16 
				         && !theCard.hasCardType(DomCardType.Treasure))
		     || (findCardToTrash()!=null 
		    		 && findCardToTrash().getName()!=DomCardName.Curse
			         && theCard.getName()==DomCardName.Curse)){
	    owner.putOnTopOfDeck(theCard);    		
	  } else {
	    owner.discard(theCard);
	  }
	}
    @Override
    public int getPlayPriority() {
      return owner.getActionsLeft()>1 ? 5 : super.getPlayPriority();
    }
}