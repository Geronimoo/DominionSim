package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.enums.DomCardName;

public class Dame_SylviaCard extends KnightCard {

    public Dame_SylviaCard() {
        super(DomCardName.Dame_Sylvia);
    }

    public void play() {
        owner.addAvailableCoins(2);
        super.play();
    }
}