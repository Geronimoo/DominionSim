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
      Collections.sort(theCards,SORT_FOR_DISCARDING);
      int i;
      for (i=0;i<3 && i<theCards.size();i++) {
          owner.discard(theCards.get(i));
      }
      for (;i<theCards.size();i++) {
          owner.putOnTopOfDeck(theCards.get(i));
      }
    }
}