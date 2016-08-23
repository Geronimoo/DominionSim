package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Bustling_VillageCard extends DomCard {
    public Bustling_VillageCard() {
      super( DomCardName.Bustling_Village);
    }

    public void play() {
      owner.addActions(3);
      owner.drawCards(1);
      DomCard theSettler = owner.removeFromDiscard(DomCardName.Settlers);
      if (theSettler!=null)
        owner.putInHand(theSettler);
    }
}