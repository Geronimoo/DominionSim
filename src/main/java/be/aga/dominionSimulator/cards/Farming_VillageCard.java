package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Farming_VillageCard extends DomCard {
    public Farming_VillageCard () {
      super( DomCardName.Farming_Village);
    }

    public void play() {
      owner.addActions(2);
      ArrayList<DomCard> theRevealedCards = owner.revealUntilActionOrTreasure();
      for (DomCard theCard : theRevealedCards) {
          if (theCard.hasCardType( DomCardType.Action) || theCard.hasCardType(DomCardType.Treasure)){
          	owner.putInHand(theCard);
          }else{
          	owner.discard(theCard);
          }
      }
    }
}