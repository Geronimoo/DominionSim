package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.enums.DomCardName;

public class Sir_DestryCard extends KnightCard {

    public Sir_DestryCard() {
        super(DomCardName.Sir_Destry);
    }

    public void play() {
        owner.drawCards(2);
        super.play();
    }
}