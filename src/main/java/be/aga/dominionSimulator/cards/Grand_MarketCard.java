package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Grand_MarketCard extends DomCard {
    public Grand_MarketCard () {
      super( DomCardName.Grand_Market);
    }

    public void play() {
      owner.addActions(1);
      owner.addAvailableCoins(2);
      owner.addAvailableBuys(1);
      owner.drawCards(1);
    }
}