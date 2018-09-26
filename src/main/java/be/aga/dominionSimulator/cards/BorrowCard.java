package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class BorrowCard extends DomCard {

    public BorrowCard() {
        super(DomCardName.Borrow);
    }

    public void play() {
        if (owner.isBorrowActivated())
            return;
        owner.addAvailableBuys(1);
        owner.addAvailableCoins(1);
        owner.setMinusOneCardToken();
        owner.setBorrowActivated();
    }
}