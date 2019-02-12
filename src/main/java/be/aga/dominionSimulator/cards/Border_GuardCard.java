package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomArtifact;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class Border_GuardCard extends DomCard {
    public Border_GuardCard() {
      super( DomCardName.Border_Guard);
    }

    public void play() {
		int theNbrToReveal = 2;
        owner.addActions(1);
        if (owner.getCurrentGame().getArtifactOwner(DomArtifact.Lantern)==owner)
        	theNbrToReveal = 3;
		ArrayList<DomCard> theRevealedCards = owner.revealTopCards(theNbrToReveal);
		if (theRevealedCards.isEmpty())
          return;
		if (theRevealedCards.size()==1) {
			owner.addCardToHand(theRevealedCards.get(0));
			return;
		}
		if (owner.isHumanOrPossessedByHuman()) {
            handleHuman(theRevealedCards);
        } else {
			Collections.sort(theRevealedCards, SORT_FOR_DISCARD_FROM_HAND);
			owner.discard(theRevealedCards.get(0));
			if (theRevealedCards.size()==2) {
				owner.addCardToHand(theRevealedCards.get(1));
			} else {
				owner.discard(theRevealedCards.get(1));
				owner.addCardToHand(theRevealedCards.get(2));
			}
		}
		if (theRevealedCards.get(0).hasCardType(DomCardType.Action) && theRevealedCards.get(1).hasCardType(DomCardType.Action)
				&& (theRevealedCards.size()<3 || theRevealedCards.get(2).hasCardType(DomCardType.Action))) {
		   if (owner.isHumanOrPossessedByHuman()) {
			   if (owner.getEngine().getGameFrame().askPlayer("<html>Take " + DomArtifact.Horn +"?</html>", "Resolving " + this.getName().toString())){
				   owner.getCurrentGame().giveArtifactTo(DomArtifact.Horn, owner);
			   } else {
				   owner.getCurrentGame().giveArtifactTo(DomArtifact.Lantern, owner);
			   }
		   } else {
			   if (owner.getCurrentGame().getArtifactOwner(DomArtifact.Horn) == owner)
				   owner.getCurrentGame().giveArtifactTo(DomArtifact.Lantern, owner);
			   else
				   owner.getCurrentGame().giveArtifactTo(DomArtifact.Horn, owner);
		   }
		}
	}

	private void handleHuman(ArrayList<DomCard> theRevealedCards) {
		owner.setNeedsToUpdateGUI();
		DomCard theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCardWithDomCard("Take in hand", theRevealedCards, "Mandatory!");
		owner.addCardToHand(theChosenCard);
		for (DomCard theCard : theRevealedCards) {
			if (theCard!=theChosenCard)
				owner.discard(theCard);
		}
	}

	@Override
	public void handleCleanUpPhase() {
    	if (owner.getCurrentGame().getArtifactOwner(DomArtifact.Horn)==owner && !owner.isHornActivated()) {
			if (owner.isHumanOrPossessedByHuman()) {
				if (owner.getEngine().getGameFrame().askPlayer("<html>Return " + DomCardName.Border_Guard.toHTML() +" to top?</html>", "Resolving " + this.getName().toString())) {
					owner.putOnTopOfDeck(this);
					owner.setHornActivated(true);
				} else {
					super.handleCleanUpPhase();
				}
			} else {
   			    owner.putOnTopOfDeck(this);
   			    owner.setHornActivated(true);
			}
		} else {
    		super.handleCleanUpPhase();
		}
	}
}