package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class PatrolCard extends DomCard {
    public PatrolCard() {
      super( DomCardName.Patrol);
    }

    public void play() {
      owner.drawCards(3);
      ArrayList< DomCard > theCards = owner.revealTopCards(4);
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman(theCards);
          return;
      }
      Collections.sort(theCards,SORT_FOR_DISCARDING);
      for (DomCard theCard:theCards) {
    	if (theCard.hasCardType(DomCardType.Victory) || theCard.hasCardType(DomCardType.Curse)){
           owner.putInHand(theCard);
    	} else {
    	   owner.putOnTopOfDeck(theCard);
    	}
      }
    }

    private void handleHuman(ArrayList<DomCard> theRevealedCards) {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCard> chooseFrom = new ArrayList<DomCard>();
        for (DomCard theCard:theRevealedCards) {
            if (theCard.hasCardType(DomCardType.Victory) || theCard.hasCardType(DomCardType.Curse)){
                owner.putInHand(theCard);
            } else {
                chooseFrom.add(theCard);
            }
        }
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>" , chooseFrom, theChosenCards, chooseFrom.size());
        for (int i=theChosenCards.size()-1;i>=0;i--) {
            for (DomCard theCard : chooseFrom) {
                if (theChosenCards.get(i).getName()==theCard.getName()) {
                    owner.putOnTopOfDeck(theCard);
                    chooseFrom.remove(theCard);
                    break;
                }
            }
        }
    }

    @Override
    public int getPlayPriority() {
        if (owner.getDeckSize()==0)
            return 1000;
        return super.getPlayPriority();
    }
}