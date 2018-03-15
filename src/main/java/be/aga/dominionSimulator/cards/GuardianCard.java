package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class GuardianCard extends DomCard {
    public GuardianCard() {
      super( DomCardName.Guardian);
    }

    public void resolveDuration() {
      owner.addAvailableCoins(1);
    }

    @Override
    public void doWhenGained() {
        owner.addCardToHand(this);
    }
}