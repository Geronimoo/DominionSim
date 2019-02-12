package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class SilosCard extends DomCard {
    public SilosCard() {
      super( DomCardName.Silos);
    }

    public void trigger() {
    	handleHuman(owner.getCardsFromHand(DomCardName.Copper));
    }

	private void handleHuman(ArrayList<DomCard> theCoppers) {
		owner.setNeedsToUpdateGUI();
		ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
		owner.getEngine().getGameFrame().askToSelectCards("<html>Discard ?</html>", theCoppers, theChosenCards, 0);
		if (theChosenCards.isEmpty())
			return;
		for (int i = theChosenCards.size() - 1; i >= 0; i--) {
			for (DomCard theCard : theCoppers) {
				if (theChosenCards.get(i).getName() == theCard.getName()) {
					owner.discardFromHand(theCard);
					theCoppers.remove(theCard);
					break;
				}
			}
		}
		owner.drawCards(theChosenCards.size());
	}

}