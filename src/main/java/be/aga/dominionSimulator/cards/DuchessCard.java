package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class DuchessCard extends DomCard {
    public DuchessCard () {
      super( DomCardName.Duchess);
    }

    public void play() {
      owner.addAvailableCoins(2);
      selectTopCard(owner);
	  for (DomPlayer player : owner.getOpponents())
		selectTopCard(player);
    }

	private void selectTopCard(DomPlayer player) {
	  ArrayList<DomCard> theRevealedCard = player.revealTopCards(1);
	  if (theRevealedCard.isEmpty()) 
		return;
	  if (theRevealedCard.get(0).getDiscardPriority(1)<16) {
		player.discard(theRevealedCard.get(0));
	  } else {
        player.putOnTopOfDeck(theRevealedCard.get(0));    		
	  }
	}
}