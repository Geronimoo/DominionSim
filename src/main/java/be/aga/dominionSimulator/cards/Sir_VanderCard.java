package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.enums.DomCardName;

public class Sir_VanderCard extends KnightCard {

    public Sir_VanderCard() {
        super(DomCardName.Sir_Vander);
    }

    public void play() {
        super.play();
    }

    @Override
    public void doWhenTrashed() {
        owner.gain(DomCardName.Gold);
    }
}