package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class Scouting_PartyCard extends DomCard {
    public Scouting_PartyCard() {
      super( DomCardName.Scouting_Party);
    }

    public void play() {
      owner.addAvailableBuys(1);
      ArrayList<DomCard> theCards = owner.revealTopCards(5);
      if (theCards.isEmpty())
          return;
      if (owner.isHumanOrPossessedByHuman()) {
         handleHuman(theCards);
         return;
      }
      Collections.sort(theCards,SORT_FOR_DISCARDING);
      int i;
      for (i=0;i<3 && i<theCards.size();i++) {
          owner.discard(theCards.get(i));
      }
      for (;i<theCards.size();i++) {
          owner.putOnTopOfDeck(theCards.get(i));
      }
    }

    private void handleHuman(ArrayList<DomCard> theRevealedCards) {
        owner.setNeedsToUpdate();
        ArrayList<DomCardName> theChosenCards = new ArrayList<DomCardName>();
        owner.getEngine().getGameFrame().askToSelectCards("<html>Discard 3</html>", theRevealedCards, theChosenCards, theRevealedCards.size() < 3 ? theRevealedCards.size() : 3);
        for (int i = theChosenCards.size() - 1; i >= 0; i--) {
            for (DomCard theCard : theRevealedCards) {
                if (theChosenCards.get(i) == theCard.getName()) {
                    owner.discard(theCard);
                    theRevealedCards.remove(theCard);
                    break;
                }
            }
        }
        if (theRevealedCards.isEmpty())
            return;
        theChosenCards = new ArrayList<DomCardName>();
        owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>", theRevealedCards, theChosenCards, 0);
        if (theChosenCards.size() < theRevealedCards.size()) {
            for (DomCard theCard : theRevealedCards) {
                owner.putOnTopOfDeck(theCard);
            }
        } else {
            for (int i = theChosenCards.size() - 1; i >= 0; i--) {
                for (DomCard theCard : theRevealedCards) {
                    if (theChosenCards.get(i) == theCard.getName()) {
                        owner.putOnTopOfDeck(theCard);
                        theRevealedCards.remove(theCard);
                        break;
                    }
                }
            }
        }
    }
}