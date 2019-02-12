package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class LackeysCard extends DomCard {
    public LackeysCard() {
      super( DomCardName.Lackeys);
    }

    public void play() {
      owner.drawCards(2);
    }

    @Override
    public void doWhenGained() {
        owner.addVillagers(2);
        super.doWhenGained();
    }
}