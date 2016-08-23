package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPhase;

public class VillaCard extends DomCard {
    public VillaCard() {
      super( DomCardName.Villa);
    }

    public void play() {
      owner.addActions(2);
      owner.addAvailableCoins(1);
      owner.addAvailableBuys(1);
    }

    @Override
    public void doWhenGained() {
        owner.addCardToHand(this);
        if (owner.getCurrentGame().getActivePlayer()==owner) {
            owner.addActions(1);
            if (owner.getPhase() == DomPhase.Buy)
                owner.triggerVilla();
        }
    }
}