package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class WindfallCard extends DomCard {
    public WindfallCard() {
      super( DomCardName.Windfall);
    }

    public void play() {
        owner.gain(DomCardName.Gold);
        owner.gain(DomCardName.Gold);
        owner.gain(DomCardName.Gold);
    }
}