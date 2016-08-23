package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class PlazaCard extends DomCard {
    public PlazaCard() {
      super( DomCardName.Plaza);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(1);
      if (!owner.getCardsFromHand(DomCardName.Copper).isEmpty()) {
          owner.discardFromHand(DomCardName.Copper);
          owner.addCoinTokens(1);
      } else {
          if (!owner.getCardsFromHand(DomCardName.Masterpiece).isEmpty()) {
              owner.discardFromHand(DomCardName.Masterpiece);
              owner.addCoinTokens(1);
          }
      }
    }
}