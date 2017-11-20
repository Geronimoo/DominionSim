package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class BardCard extends DomCard {
    public BardCard() {
      super( DomCardName.Bard);
    }

    public void play() {
      owner.addAvailableCoins(2);
      owner.receiveBoon(null);
    }
}