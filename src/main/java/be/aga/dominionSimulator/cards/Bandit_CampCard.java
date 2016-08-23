package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Bandit_CampCard extends DomCard {
    public Bandit_CampCard () {
      super( DomCardName.Bandit_Camp);
    }

    public void play() {
       	owner.drawCards(1);
       	owner.addActions(2);
        owner.gain(DomCardName.Spoils);
    }
}