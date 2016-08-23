package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class EnvoyCard extends DomCard {
    public EnvoyCard () {
      super( DomCardName.Envoy);
    }

    public void play() {
      ArrayList<DomCard> theRevealedCards = owner.revealTopCards( 5 );
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
    public int getPlayPriority() {
      return owner.getActionsLeft()>1 ? 8 : super.getPlayPriority();
    }
}