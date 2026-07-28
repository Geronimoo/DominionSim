package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class GambleCard extends DomCard {
    public GambleCard() {
      super( DomCardName.Gamble);
    }

    public void play() {
      owner.addAvailableBuys(1);
      DomCard theDiscardedCard = owner.discardTopCardFromDeck();
      if ( theDiscardedCard==null)
          return;
      if (theDiscardedCard.hasCardType(DomCardType.Action) || theDiscardedCard.hasCardType(DomCardType.Treasure)) {
          owner.play(owner.removeCardFromDiscard(theDiscardedCard));
      }
    }
}