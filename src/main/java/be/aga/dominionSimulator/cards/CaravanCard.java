package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class CaravanCard extends DomCard {
    public CaravanCard () {
      super( DomCardName.Caravan);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
    }

    public void resolveDuration() {
      owner.drawCards(1);
    }
}