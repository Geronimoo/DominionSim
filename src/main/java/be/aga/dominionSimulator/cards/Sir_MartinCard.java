package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.enums.DomCardName;

public class Sir_MartinCard extends KnightCard {

    public Sir_MartinCard() {
        super(DomCardName.Sir_Martin);
    }

    public void play() {
        owner.addAvailableBuys(2);
        super.play();
    }
}