package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class Night_WatchmanCard extends DomCard {
    public Night_WatchmanCard() {
      super( DomCardName.Night_Watchman);
    }

    public void play() {
		ArrayList<DomCard> theRevealedCards = owner.revealTopCards(5);
		if (theRevealedCards.isEmpty())
          return;
		if (owner.isHumanOrPossessedByHuman()) {
            handleHuman(theRevealedCards);
            return;
        }
		Collections.sort(theRevealedCards,SORT_FOR_DISCARDING);
		for (DomCard theRevealedCard : theRevealedCards){
            if (theRevealedCard.getDiscardPriority(1)< DomCardName.Silver.getDiscardPriority(0)) {
              owner.discard(theRevealedCard);
            } else {
              owner.putOnTopOfDeck(theRevealedCard);
            }
        }
	}

	private void handleHuman(ArrayList<DomCard> theRevealedCards) {
		owner.setNeedsToUpdateGUI();
		ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
		owner.getEngine().getGameFrame().askToSelectCards("<html>Discard ?</html>", theRevealedCards, theChosenCards, 0);
		for (int i = theChosenCards.size() - 1; i >= 0; i--) {
			for (DomCard theCard : theRevealedCards) {
				if (theChosenCards.get(i).getName() == theCard.getName()) {
					owner.discard(theCard);
					theRevealedCards.remove(theCard);
					break;
				}
			}
		}
		if (theRevealedCards.isEmpty())
			return;
		theChosenCards = new ArrayList<DomCard>();
		owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>", theRevealedCards, theChosenCards, 0);
		if (theChosenCards.size() < theRevealedCards.size()) {
			for (DomCard theCard : theRevealedCards) {
				owner.putOnTopOfDeck(theCard);
			}
		} else {
			for (int i = theChosenCards.size() - 1; i >= 0; i--) {
				for (DomCard theCard : theRevealedCards) {
					if (theChosenCards.get(i).getName() == theCard.getName()) {
						owner.putOnTopOfDeck(theCard);
						theRevealedCards.remove(theCard);
						break;
					}
				}
			}
		}
	}

	@Override
	public void doWhenGained() {
		owner.addCardToHand(this);
	}
}