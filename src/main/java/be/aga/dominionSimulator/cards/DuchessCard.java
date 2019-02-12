package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

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
	  if (player.isHumanOrPossessedByHuman()) {
		  if (owner.getEngine().getGameFrame().askPlayer("<html>Discard " + theRevealedCard.get(0).getName().toHTML() +" ?</html>", "Resolving " + this.getName().toString())) {
			  player.discard(theRevealedCard.get(0));
		  } else {
			  player.putOnTopOfDeck(theRevealedCard.get(0));
		  }
	  } else {
		  if (theRevealedCard.get(0).getDiscardPriority(1) < 16) {
			  player.discard(theRevealedCard.get(0));
		  } else {
			  player.putOnTopOfDeck(theRevealedCard.get(0));
		  }
	  }
	}

	@Override
	public boolean hasCardType(DomCardType aType) {
		if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
			return true;
		return super.hasCardType(aType);
	}

}