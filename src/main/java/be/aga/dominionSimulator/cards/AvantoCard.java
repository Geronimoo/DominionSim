package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class AvantoCard extends DomCard {
    public AvantoCard() {
      super( DomCardName.Avanto);
    }

    public void play() {
      owner.drawCards(3);
      if (!owner.getCardsFromHand(DomCardName.Sauna).isEmpty()) {
    	  owner.play(owner.removeCardFromHand( owner.getCardsFromHand(DomCardName.Sauna).get(0)));
      }
    }
}