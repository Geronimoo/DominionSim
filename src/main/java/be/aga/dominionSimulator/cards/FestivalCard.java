package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class FestivalCard extends DomCard {
    public FestivalCard () {
      super( DomCardName.Festival);
    }

    public void play() {
      owner.addActions(2);
      owner.addAvailableCoins(2);
      owner.addAvailableBuys(1);
    }
}