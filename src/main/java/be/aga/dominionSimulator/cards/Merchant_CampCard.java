package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Merchant_CampCard extends DomCard {
    public Merchant_CampCard() {
      super( DomCardName.Merchant_Camp);
    }

    public void play() {
      owner.addActions(2);
      owner.addAvailableCoins(1);
    }
}