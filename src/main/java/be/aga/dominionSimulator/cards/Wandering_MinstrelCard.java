package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import java.util.ArrayList;
import java.util.Collections;

public class Wandering_MinstrelCard extends DomCard {
    public Wandering_MinstrelCard() {
      super( DomCardName.Wandering_Minstrel);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(1);
      if (owner.getDeckSize()==0)
    	return;
      ArrayList<DomCard> theRevealedCards = owner.revealTopCards(3);
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman(theRevealedCards);
          return;
      }
      Collections.sort(theRevealedCards,SORT_FOR_DISCARDING);
      for (DomCard theCard : theRevealedCards){
          if (theCard.hasCardType(DomCardType.Action))
              owner.putOnTopOfDeck(theCard);
          else
              owner.discard(theCard);
      }
    }

    private void handleHuman(ArrayList<DomCard> aRevealedCards) {
        owner.setNeedsToUpdate();
        ArrayList<DomCard> theChooseFrom = new ArrayList<DomCard>();
        for (DomCard theCard : aRevealedCards) {
            if (theCard.hasCardType(DomCardType.Action))
                theChooseFrom.add(theCard);
            else
                owner.discard(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>", theChooseFrom, theChosenCards, 0);
        if (theChosenCards.size() < theChooseFrom.size()) {
            for (DomCard theCard : theChooseFrom) {
                owner.putOnTopOfDeck(theCard);
            }
        } else {
            for (int i = theChosenCards.size() - 1; i >= 0; i--) {
                for (DomCard theCard : theChooseFrom) {
                    if (theChosenCards.get(i).getName() == theCard.getName()) {
                        owner.putOnTopOfDeck(theCard);
                        theChooseFrom.remove(theCard);
                        break;
                    }
                }
            }
        }


    }
}