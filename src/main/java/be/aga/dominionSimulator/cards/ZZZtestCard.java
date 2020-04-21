package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class ZZZtestCard extends DomCard {
    public ZZZtestCard() {
      super( DomCardName.ZZZtest);
    }

    public void play() {
        owner.gain(DomCardName.Silver);
        owner.addActions(1);
        owner.drawCards(1);
    }
}