package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class PeasantCard extends DomCard {
    public PeasantCard() {
      super( DomCardName.Peasant);
    }

    public void play() {
      owner.addAvailableCoins(1);
      owner.addAvailableBuys(1);
    }

    @Override
    public void handleCleanUpPhase() {
        if (owner==null)
            return;
        if (owner.wants(DomCardName.Soldier)) {
            DomPlayer theOwner = owner;
            owner.returnToSupply(this);
            theOwner.gain(DomCardName.Soldier);
            return;
        }
        super.handleCleanUpPhase();
    }
}