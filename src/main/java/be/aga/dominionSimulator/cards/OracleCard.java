package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class OracleCard extends DomCard {
    public OracleCard () {
      super( DomCardName.Oracle);
    }

    public void play() {
      oracleYourself();
      oracleOpponents();
      owner.drawCards(2);
    }

	private void oracleOpponents() {
		for (DomPlayer thePlayer : owner.getOpponents()) {
		  if (thePlayer.checkDefense()) 
	    	continue;
	      ArrayList<DomCard> theRevealedCards = thePlayer.revealTopCards(2);
	      if (theRevealedCards.isEmpty()) 
	    	continue;
	      if (owner.isHumanOrPossessedByHuman()) {
	      	handleOpponentAsHuman(thePlayer, theRevealedCards);
	      	return;
		  }
	      Collections.sort(theRevealedCards, SORT_FOR_DISCARDING);
	      int theTotalValue=0;
		  for (DomCard theCard : theRevealedCards) {
		    theTotalValue+=theCard.getDiscardPriority(1); 
	      }
	      if (theTotalValue>=32){
	    	thePlayer.discard(theRevealedCards);
	      }else{
	      	  if (thePlayer.isHuman() && theRevealedCards.size()>1) {
				  ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
				  do {
					  owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>", theRevealedCards, theChosenCards, 0);
				  } while (theChosenCards.size()==1);
				  if (theChosenCards.size()<2) {
					  thePlayer.putOnTopOfDeck(theRevealedCards.get(1));
					  thePlayer.putOnTopOfDeck(theRevealedCards.get(0));
				  } else {
					  if (theRevealedCards.get(1).getName() == theChosenCards.get(1).getName()) {
						  thePlayer.putOnTopOfDeck(theRevealedCards.get(1));
						  thePlayer.putOnTopOfDeck(theRevealedCards.get(0));
					  } else {
						  thePlayer.putOnTopOfDeck(theRevealedCards.get(0));
						  thePlayer.putOnTopOfDeck(theRevealedCards.get(1));
					  }
				  }
			  } else {
				  for (DomCard theCard : theRevealedCards) {
					  thePlayer.putOnTopOfDeck(theCard);
				  }
			  }
	      }
	    }
	}

	private void handleOpponentAsHuman(DomPlayer thePlayer, ArrayList<DomCard> theRevealedCards) {
		if (owner.getEngine().getGameFrame().askPlayer("<html>Discard Opponent's " + theRevealedCards +" ?</html>", "Resolving " + this.getName().toString())) {
			thePlayer.discard(theRevealedCards);
		} else {
			Collections.sort(theRevealedCards, SORT_FOR_DISCARDING);
			for (DomCard theCard : theRevealedCards) {
 			  thePlayer.putOnTopOfDeck(theCard);
			}
		}
	}

	private void oracleYourself() {
	  ArrayList<DomCard> theRevealedCards = owner.revealTopCards(2);
      if (theRevealedCards.isEmpty()) 
    	return;
      if (owner.isHumanOrPossessedByHuman()) {
			handleHuman(theRevealedCards);
			return;
	  }
      int theTotalValue = 0;
      for (DomCard theCard : theRevealedCards) {
    	theTotalValue+=theCard.getDiscardPriority(owner.getActionsLeft()); 
      }
      if (theTotalValue<32){
    	owner.discard(theRevealedCards);
      }else{
	      for (DomCard theCard : theRevealedCards) {
	    	  owner.putOnTopOfDeck(theCard); 
	      }
      }
	}

	private void handleHuman(ArrayList<DomCard> theRevealedCards) {
		if (owner.getEngine().getGameFrame().askPlayer("<html>Discard " + theRevealedCards +" ?</html>", "Resolving " + this.getName().toString())) {
			owner.discard(theRevealedCards);
		} else {
			if (theRevealedCards.size()==1) {
				owner.putOnTopOfDeck(theRevealedCards.get(0));
				return;
			}
			ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
			do {
				owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>", theRevealedCards, theChosenCards, 0);
			} while (theChosenCards.size()==1);
			if (theChosenCards.size()<2) {
				owner.putOnTopOfDeck(theRevealedCards.get(1));
				owner.putOnTopOfDeck(theRevealedCards.get(0));
			} else {
				if (theRevealedCards.get(1).getName() == theChosenCards.get(1).getName()) {
					owner.putOnTopOfDeck(theRevealedCards.get(1));
					owner.putOnTopOfDeck(theRevealedCards.get(0));
				} else {
					owner.putOnTopOfDeck(theRevealedCards.get(0));
					owner.putOnTopOfDeck(theRevealedCards.get(1));
				}
			}
		}
	}
}