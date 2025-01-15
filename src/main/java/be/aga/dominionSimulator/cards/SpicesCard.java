package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class SpicesCard extends DomCard {
    public SpicesCard() {
      super( DomCardName.Spices);
    }

    public void play() {
      owner.addAvailableCoins(2);
      owner.addAvailableBuys(1);
    }

    @Override
    public void doWhenGained() {
        owner.addCoffers(2);
        super.doWhenGained();
    }
}