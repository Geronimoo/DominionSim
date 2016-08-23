package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class VentureCard extends DomCard {
    public VentureCard () {
      super( DomCardName.Venture);
    }

    public void play() {
      owner.addAvailableCoins(1);
      ArrayList< DomCard > theRevealedCards = owner.revealUntilType( DomCardType.Treasure);
      for (DomCard theCard : theRevealedCards) {
        if (theCard.hasCardType( DomCardType.Treasure )) {
          owner.play(theCard);
        } else {
          owner.discard( theCard );
        }
      }
  }
}