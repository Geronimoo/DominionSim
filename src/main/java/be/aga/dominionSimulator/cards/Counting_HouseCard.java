package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Counting_HouseCard extends DomCard {
    public Counting_HouseCard () {
      super( DomCardName.Counting_House);
    }

    public void play() {
      ArrayList<DomCard> theCoppers = owner.removeCardsFromDiscard(DomCardName.Copper);
      for (DomCard theCard : theCoppers) {
        owner.putInHand(theCard);
      }
    }
}