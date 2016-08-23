package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Caravan_GuardCard extends DomCard {
    public Caravan_GuardCard() {
      super( DomCardName.Caravan_Guard);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
    }

    public void resolveDuration() {
      owner.addAvailableCoins(1);
    }
}