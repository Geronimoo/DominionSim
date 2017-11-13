package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class Zombie_SpyCard extends DomCard {
    public Zombie_SpyCard() {
      super( DomCardName.Zombie_Spy);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      spyOnYourself();
    }

	private void spyOnYourself() {
		ArrayList<DomCard> theRevealedCard = owner.revealTopCards(1);
		  if (theRevealedCard.isEmpty()) 
			  return;
		  if (owner.isHumanOrPossessedByHuman()) {
		  	  owner.setNeedsToUpdate();
			  if (owner.getEngine().getGameFrame().askPlayer("<html>Discard " + theRevealedCard.get(0).getName().toHTML() +"?</html>", "Resolving " + this.getName().toString())){
				  owner.discard(theRevealedCard);
			  } else {
				  owner.putOnTopOfDeck(theRevealedCard.get(0));
			  }
		  } else {
			  if (theRevealedCard.get(0).getDiscardPriority(1) < 16) {
				  owner.discard(theRevealedCard);
			  } else {
				  owner.putOnTopOfDeck(theRevealedCard.get(0));
			  }
		  }
	}
}