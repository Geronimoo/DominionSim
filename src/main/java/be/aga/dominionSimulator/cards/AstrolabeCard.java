package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class AstrolabeCard extends DomCard {
    public AstrolabeCard() {
      super( DomCardName.Astrolabe);
    }

    public void play() {
      owner.addAvailableCoins(1);
      owner.addAvailableBuys(1);
    }

    @Override
    public void resolveDuration() {
        owner.addAvailableCoins(1);
        owner.addAvailableBuys(1);
    }
}