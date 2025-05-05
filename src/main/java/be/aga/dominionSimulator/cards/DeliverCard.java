package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class DeliverCard extends DomCard {
    public DeliverCard() {
      super( DomCardName.Deliver);
    }

    public void play() {
        owner.addAvailableBuys(1);
        owner.triggerDeliver();
    }
}