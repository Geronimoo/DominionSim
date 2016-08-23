package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class DoctorCard extends DomCard {
    public DoctorCard() {
      super( DomCardName.Doctor);
    }

    public void play() {
      DomCardName theChoice=owner.getDeck().getMostLikelyCrappyCard();
      if (theChoice==null) {
          theChoice=DomCardName.Curse;
      }
      if (DomEngine.haveToLog) DomEngine.addToLog(owner + " names " + theChoice.toHTML());
      ArrayList<DomCard> theRevealedCards = owner.revealTopCards(3);
      ArrayList<DomCard> theCardsToPutBack = new ArrayList<DomCard>();
      for (DomCard theCard : theRevealedCards) {
          if (theCard.getName()==theChoice)
              owner.trash(theCard);
          else
              theCardsToPutBack.add(theCard);
      }
      Collections.sort(theCardsToPutBack, DomCard.SORT_FOR_DISCARDING);
      for (DomCard card:theCardsToPutBack) {
          owner.putOnTopOfDeck(card);
      }
    }

    public void doWhenBought() {
        for (int i=0;i<owner.getTotalAvailableCoins();i++) {
            ArrayList<DomCard> theRevealedCard = owner.revealTopCards(1);
            if (theRevealedCard.isEmpty())
                return;
            if (theRevealedCard.get(0).getTrashPriority()<=DomCardName.Copper.getTrashPriority(owner)) {
                owner.trash(theRevealedCard.get(0));
            } else {
                if (i==owner.getTotalAvailableCoins()-1 && theRevealedCard.get(0).getDiscardPriority(1)>20) {
                    owner.putOnTopOfDeck(theRevealedCard.get(0));
                } else {
                    owner.discard(theRevealedCard);
                }
            }
        }
        owner.setAvailableCoins(0);
        owner.setCoinTokens(0);
    }
}