package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class VillageCard extends DomCard {
    public VillageCard () {
      super( DomCardName.Village);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(1);
    }
}