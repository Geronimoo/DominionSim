package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class Dame_MollyCard extends KnightCard {

    public Dame_MollyCard() {
        super(DomCardName.Dame_Molly);
    }

    public void play() {
        owner.addActions(2);
        super.play();
    }
}