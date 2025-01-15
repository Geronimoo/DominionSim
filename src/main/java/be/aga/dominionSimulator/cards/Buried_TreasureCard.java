package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Buried_TreasureCard extends DomCard {
    public Buried_TreasureCard() {
      super( DomCardName.Buried_Treasure);
    }

    @Override
    public void resolveDuration() {
        owner.addAvailableCoins(3);
        owner.addAvailableBuys(1);
    }

    @Override
    public void doWhenGained() {
        if (owner.getCardsFromDiscard().contains(this)) {
            owner.play(owner.removeCardFromDiscard(this));
        }
    }
}