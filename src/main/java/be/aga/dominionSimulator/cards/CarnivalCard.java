package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.HashSet;

public class CarnivalCard extends DomCard {
    public CarnivalCard() {
      super( DomCardName.Carnival);
    }

    public void play() {
      boolean recordNumbers = false;
      if (owner.getDeck().getDeckAndDiscardSize() > 3)
          recordNumbers=true;
      ArrayList< DomCard > theCards = owner.revealTopCards(4);
  	  HashSet<DomCardName> theSingleCards = new HashSet<DomCardName>();
      ArrayList<DomCard> toRemoveCards = new ArrayList<>();
      for (DomCard card : theCards) {
          if (!theSingleCards.contains(card.getName())) {
              owner.putInHand(card);
              theSingleCards.add(card.getName());
          } else {
              toRemoveCards.add(card);
          }
      }
      owner.discard(toRemoveCards);

      if (recordNumbers) {
          owner.addCarnivalsPlayed();
          owner.addCarnivalDraws(theSingleCards.size());
      }
    }

    @Override
    public boolean wantsToBePlayed() {
        return owner.getDeck().getDeckAndDiscardSize() > 0;
    }
}