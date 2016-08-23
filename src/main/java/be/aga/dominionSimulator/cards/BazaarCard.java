package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class BazaarCard extends DomCard {
    public BazaarCard () {
      super( DomCardName.Bazaar);
    }

    public void play() {
      owner.addActions(2);
      owner.addAvailableCoins(1);
      owner.drawCards(1);
    }
}