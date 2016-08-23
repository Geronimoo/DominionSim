package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Treasure_TroveCard extends DomCard {
    public Treasure_TroveCard() {
      super( DomCardName.Treasure_Trove);
    }

    public void play() {
      owner.addAvailableCoins(2);
      owner.gain(DomCardName.Gold);
      owner.gain(DomCardName.Copper);
    }
}