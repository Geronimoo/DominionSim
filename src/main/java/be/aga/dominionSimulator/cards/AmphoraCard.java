package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class AmphoraCard extends DomCard {
    private boolean durationFailed=false;

    public AmphoraCard() {
      super( DomCardName.Amphora);
    }

    public void play() {
        durationFailed=true;
        //TODO try to find cases where it waits
        owner.addAvailableCoins(3);
        owner.addAvailableBuys(1);
    }

    private void handleHuman() {
        //TODO
        owner.addAvailableCoins(3);
        owner.addAvailableBuys(1);
    }

    public boolean durationFailed() {
        return durationFailed;
    }

    @Override
    public void resolveDuration() {
        owner.addAvailableCoins(3);
        owner.addAvailableBuys(1);
    }
}