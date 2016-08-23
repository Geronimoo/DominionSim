package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class AdvisorCard extends DomCard {
    public AdvisorCard() {
      super( DomCardName.Advisor);
    }

    public void play() {
      owner.addActions(1);
      ArrayList<DomCard> theRevealedCards = owner.revealTopCards( 3 );
      discardBestCard(theRevealedCards);
      owner.getCardsInHand().addAll(theRevealedCards);
    }

    private final void discardBestCard( ArrayList< DomCard > aCardList) {
        if (!aCardList.isEmpty()){
          Collections.sort(aCardList,SORT_FOR_DISCARD_FROM_HAND);
          owner.discard( aCardList.remove( aCardList.size()-1 ) );
        }
    }

    @Override
    public boolean wantsToBePlayed() {
        return owner.getDeckSize()>1;
    }
}