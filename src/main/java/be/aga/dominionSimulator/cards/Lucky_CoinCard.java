package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

public class Lucky_CoinCard extends DomCard {

    public Lucky_CoinCard() {
        super(DomCardName.Lucky_Coin);
    }

    @Override
    public void play() {
        owner.addAvailableCoins(1);
        owner.gain(DomCardName.Silver);
    }
}