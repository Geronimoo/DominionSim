package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class HeraldCard extends DomCard {
    public HeraldCard() {
      super( DomCardName.Herald);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      if (owner.getDeckSize()==0)
    	return;
      DomCard theRevealedCard = owner.revealTopCards(1).get(0);
      if (theRevealedCard.hasCardType(DomCardType.Action))
        owner.play(theRevealedCard);
      else
        owner.putOnTopOfDeck(theRevealedCard);
    }

    public void doWhenBought() {
        if (owner.getAvailableCoins()==0)
            return;
        if (owner.getBuysLeft()>0 && owner.getDesiredCard(owner.getTotalAvailableCurrency(),false)!=null)
          return;
        Collections.sort(owner.getCardsFromDiscard(),SORT_FOR_DISCARDING);
        ArrayList<DomCard> theCardsToConsider = new ArrayList<DomCard>();
        for (int i=owner.getCardsFromDiscard().size()-1;i>=0;i--) {
            if (owner.getCardsFromDiscard().get(i).getDiscardPriority(1)>=DomCardName.Silver.getDiscardPriority(1))
                if (owner.getCardsFromDiscard().get(i)!=this)
                    theCardsToConsider.add(owner.getCardsFromDiscard().get(i));
        }
        if (theCardsToConsider.isEmpty())
            return;
        int theTotalCards = Math.min(theCardsToConsider.size(), owner.getAvailableCoins());
        if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " overbuys for $" + theTotalCards );
        owner.spend(theTotalCards);
        for (int i=0;i<theTotalCards;i++) {
            owner.putOnTopOfDeck(owner.removeCardFromDiscard(theCardsToConsider.get(i)));
        }
    }
}