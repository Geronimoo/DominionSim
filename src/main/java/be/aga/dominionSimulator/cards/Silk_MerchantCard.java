package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Silk_MerchantCard extends DomCard {
    public Silk_MerchantCard() {
      super( DomCardName.Silk_Merchant);
    }

    public void play() {
        owner.drawCards(2);
        owner.addAvailableBuys(1);
    }

    @Override
    public void doWhenGained() {
        owner.addCoffers(1);
        owner.addVillagers(1);
    }

    @Override
    public void doWhenTrashed() {
        owner.addCoffers(1);
        owner.addVillagers(1);
    }
}