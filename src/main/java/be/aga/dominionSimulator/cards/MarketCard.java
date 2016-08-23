package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class MarketCard extends DomCard {
    public MarketCard () {
      super( DomCardName.Market);
    }

    public void play() {
      owner.addActions(1);
      owner.addAvailableCoins(1);
      owner.addAvailableBuys(1);
      owner.drawCards(1);
    }
}