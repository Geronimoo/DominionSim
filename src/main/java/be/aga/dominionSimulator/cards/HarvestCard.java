package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.HashSet;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class HarvestCard extends DomCard {
    public HarvestCard () {
      super( DomCardName.Harvest);
    }

    public void play() {
      ArrayList< DomCard > theCards = owner.revealTopCards(4);
  	  HashSet<DomCardName> theSingleCards = new HashSet<DomCardName>();
      for (DomCard card : theCards)
          theSingleCards.add(card.getName());
      owner.addAvailableCoins(theSingleCards.size());
      owner.discard(theCards);
    }
}