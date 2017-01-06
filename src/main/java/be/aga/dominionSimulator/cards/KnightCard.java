package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class KnightCard extends DomCard {
    public KnightCard(DomCardName aName) {
      super( aName );
    }

    public void play() {
        //theOwner needed in multiplayer game when this card will be trashed mid-play
        DomPlayer theOwner = owner;
        for (DomPlayer thePlayer : theOwner.getOpponents()) {
          if (thePlayer.checkDefense() )
              continue;
          ArrayList< DomCard > theRevealedCards = thePlayer.revealTopCards(2);
          if (theRevealedCards.isEmpty())
              continue;
          Collections.sort(theRevealedCards,SORT_FOR_TRASHING);
          DomCard theCard = theRevealedCards.get(0);
          while (theCard!=null && (theCard.getCoinCost(theOwner.getCurrentGame()) <3 || theCard.getCoinCost(theOwner.getCurrentGame()) > 6 || theCard.getPotionCost()>0 || theCard.getDebtCost()>0) ) {
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
          if (theCardToTrash.hasCardType(DomCardType.Knight) && owner!=null && !owner.getCardsFromPlay(getName()).isEmpty())
              owner.trash(owner.removeCardFromPlay(this));
          if (!theRevealedCards.isEmpty())
              thePlayer.discard(theRevealedCards);
        }
    }
}