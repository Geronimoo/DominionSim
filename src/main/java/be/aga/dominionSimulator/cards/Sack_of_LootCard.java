package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Sack_of_LootCard extends DomCard {

    public Sack_of_LootCard() {
      super( DomCardName.Sack_of_Loot);
    }

    @Override
    public void play() {
        owner.addAvailableCoins(1);
        owner.gainLoot();
    }
}