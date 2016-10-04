package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class Wine_MerchantCard extends DomCard {
    public Wine_MerchantCard() {
      super( DomCardName.Wine_Merchant);
    }

    public void play() {
        owner.addAvailableBuys(1);
        owner.addAvailableCoins(4);
        if (!owner.getCardsFromPlay(DomCardName.Wine_Merchant).isEmpty())
          owner.putOnTavernMat(owner.removeCardFromPlay(this));
    }
}