package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.enums.DomCardName;

public class Sir_BaileyCard extends KnightCard {

    public Sir_BaileyCard() {
        super(DomCardName.Sir_Bailey);
    }

    public void play() {
        owner.addActions(1);
        owner.drawCards(1);
        super.play();
    }
}