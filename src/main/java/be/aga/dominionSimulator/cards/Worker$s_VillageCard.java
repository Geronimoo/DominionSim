package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Worker$s_VillageCard extends DomCard {
    public Worker$s_VillageCard () {
      super( DomCardName.Worker$s_Village);
    }

    public void play() {
      owner.addActions(2);
      owner.addAvailableBuys(1);
      owner.drawCards(1);
    }
}