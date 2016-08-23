package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class BorrowCard extends DomCard {

    public BorrowCard() {
        super(DomCardName.Borrow);
    }

    public void play() {
        owner.addAvailableBuys(1);
        owner.addAvailableCoins(1);
        owner.setMinusOneCardToken();
    }
}