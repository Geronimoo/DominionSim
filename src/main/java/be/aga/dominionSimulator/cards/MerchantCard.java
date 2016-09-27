package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class MerchantCard extends DomCard {
    public MerchantCard() {
      super( DomCardName.Merchant);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      owner.addMerchantPlayed();

    }
}