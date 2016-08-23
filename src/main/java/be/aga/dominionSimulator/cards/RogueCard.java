package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class RogueCard extends DomCard {
    public RogueCard() {
      super( DomCardName.Rogue );
    }

    public void play() {
        owner.addAvailableCoins(2);
        ArrayList<DomCard> theRogueableCards = owner.getCurrentGame().getRogueableCardsInTrash();
        if (!theRogueableCards.isEmpty()) {
            Collections.sort(theRogueableCards, SORT_FOR_TRASHING);
            owner.gain(owner.getCurrentGame().removeFromTrash(theRogueableCards.get(theRogueableCards.size() - 1)));
            return;
        }
        for (DomPlayer thePlayer : owner.getOpponents()) {
          if (thePlayer.checkDefense() )
              continue;
          ArrayList< DomCard > theRevealedCards = thePlayer.revealTopCards(2);
          if (theRevealedCards.isEmpty())
              continue;
          Collections.sort(theRevealedCards,SORT_FOR_TRASHING);
          DomCard theCard = theRevealedCards.get(0);
          while (theCard!=null && (theCard.getCoinCost(owner.getCurrentGame()) <3 || theCard.getCoinCost(owner.getCurrentGame()) > 6 || theCard.getPotionCost()>0)) {
              thePlayer.discard(theRevealedCards.remove(0));
              if (theRevealedCards.isEmpty())
                  theCard=null;
              else
                  theCard = theRevealedCards.get(0);
          }
          if (theRevealedCards.isEmpty())
              continue;
          DomCard theCardToTrash = theRevealedCards.remove(0);
          thePlayer.trash(theCardToTrash);
          if (!theRevealedCards.isEmpty())
              thePlayer.discard(theRevealedCards);
        }
    }
}