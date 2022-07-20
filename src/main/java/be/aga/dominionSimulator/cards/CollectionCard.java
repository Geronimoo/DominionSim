package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class CollectionCard extends DomCard {
    public CollectionCard() {
      super( DomCardName.Collection);
    }

    public void play() {
        owner.addAvailableCoins(2);
        owner.addAvailableBuys(1);
        owner.addCollectionTrigger();
    }

}