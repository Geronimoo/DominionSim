package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Snowy_VillageCard extends DomCard {
    public Snowy_VillageCard() {
      super( DomCardName.Snowy_Village);
    }

    public void play() {
      owner.addActions(4);
      owner.drawCards(1);
      owner.addAvailableBuys(1);
      owner.setSnowedIn();
    }
}