package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Candlestick_MakerCard extends DomCard {
    public Candlestick_MakerCard() {
      super( DomCardName.Candlestick_Maker);
    }

    public void play() {
      owner.addActions(1);
      owner.addCoinTokens(1);
      owner.addAvailableBuys(1);
    }
}