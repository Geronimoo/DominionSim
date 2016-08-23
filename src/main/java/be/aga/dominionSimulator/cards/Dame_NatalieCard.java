package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

public class Dame_NatalieCard extends KnightCard {

    public Dame_NatalieCard() {
        super(DomCardName.Dame_Natalie);
    }

    public void play() {
        DomCardName theDesiredCard = owner.getDesiredCard(new DomCost( 3, 0), false);
        if (theDesiredCard!=null)
          owner.gain(theDesiredCard);
        super.play();
    }
}