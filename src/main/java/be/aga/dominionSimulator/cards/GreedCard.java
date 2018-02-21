package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class GreedCard extends DomCard {
    public GreedCard() {
      super( DomCardName.Greed);
    }

    public void play() {
        DomCard theCopper = owner.getCurrentGame().takeFromSupply(DomCardName.Copper);
        if (theCopper==null)
            return;
        owner.gainOnTopOfDeck(theCopper);
    }
}