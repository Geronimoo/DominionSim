package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class LaboratoryCard extends DomCard {
    public LaboratoryCard () {
      super( DomCardName.Laboratory);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(2);
    }
}