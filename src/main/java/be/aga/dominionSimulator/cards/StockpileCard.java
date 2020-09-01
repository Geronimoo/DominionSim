package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class StockpileCard extends DomCard {
    public StockpileCard() {
      super( DomCardName.Stockpile);
    }

    public void play() {
        owner.addAvailableCoins(3);
        owner.addAvailableBuys(1);
        if (owner.getCardsInPlay().contains(this)) {
            //this is possible if card was Counterfeited
            owner.exile(owner.removeCardFromPlay(this));
        }
    }
}