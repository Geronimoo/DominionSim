package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class LitterCard extends DomCard {
    public LitterCard() {
      super( DomCardName.Litter);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(2);
      owner.addDebt(1);
    }
}