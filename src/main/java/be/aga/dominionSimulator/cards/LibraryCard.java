package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class LibraryCard extends DrawUntilXCardsCard {
    private ArrayList<DomCard> myPutAsideCards=new ArrayList<DomCard>();

    public LibraryCard () {
      super( DomCardName.Library);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman()) {
          handleHumanPlayer();
      } else {
          while (owner.getCardsInHand().size() < 7 && !owner.isDeckEmpty()) {
              DomCard theRevealedCard = owner.revealTopCards(1).get(0);
              if (theRevealedCard.hasCardType(DomCardType.Action)
                      && (owner.getActionsAndVillagersLeft() == 0
                      || (theRevealedCard.hasCardType(DomCardType.Terminal) && owner.getProbableActionsLeft() <= 0))) {
                  putCardAside(theRevealedCard);
              } else {
                  owner.putInHand(theRevealedCard);
              }
          }
          discardPutAsideCards();
          owner.showHand();
      }
    }

    private void handleHumanPlayer() {
        while (owner.getCardsInHand().size() < 7 && !owner.isDeckEmpty()) {
            DomCard theRevealedCard = owner.revealTopCards(1).get(0);
            if (theRevealedCard.hasCardType(DomCardType.Action) && owner.getEngine().getGameFrame().askPlayer("<html>Skip " + theRevealedCard.getName().toHTML() + " ?</html>", "Resolving " + this.getName().toString())) {
               putCardAside(theRevealedCard);
            } else {
               owner.putInHand(theRevealedCard);
            }
        }
        discardPutAsideCards();
    }

    private void discardPutAsideCards() {
		owner.discard(myPutAsideCards);
		myPutAsideCards.clear();
	}

	private void putCardAside(DomCard theRevealedCard) {
		if (DomEngine.haveToLog) DomEngine.addToLog("... and puts it aside");
		myPutAsideCards.add(theRevealedCard);
	}

    @Override
    public int getPlayPriority() {
        if (owner.getActionsAndVillagersLeft()==2
          && owner.getCardsFromHand(DomCardType.Action).size()==owner.getCardsFromHand(DomCardType.Terminal).size())
                return 5;
        return super.getPlayPriority();
    }
}