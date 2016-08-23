package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class SageCard extends DomCard {
    public SageCard() {
      super( DomCardName.Sage);
    }

    public void play() {
      owner.addActions(1);
      ArrayList< DomCard > theCards = owner.revealUntilCost(3);
      for (DomCard card : theCards) {
  	   if (card.getCoinCost(owner.getCurrentGame())>=3)
         owner.addCardToHand(card);
       else
         owner.discard(card);
      }
    }
}