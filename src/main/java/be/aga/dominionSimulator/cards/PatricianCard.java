package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class PatricianCard extends DomCard {
    public PatricianCard() {
      super( DomCardName.Patrician);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      ArrayList<DomCard> theTopCard = owner.revealTopCards(1);
      if (theTopCard.isEmpty())
          return;
      if (theTopCard.get(0).getCost(owner.getCurrentGame()).compareTo(new DomCost(5,0))>=0)
          owner.addCardToHand(theTopCard.get(0));
      else
          owner.putOnTopOfDeck(theTopCard.get(0));
    }
}