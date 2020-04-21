package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class DestrierCard extends DomCard {
    public DestrierCard() {
      super( DomCardName.Destrier);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(2);
    }
}