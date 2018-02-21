package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class HauntingCard extends DomCard {
    public HauntingCard() {
      super( DomCardName.Haunting);
    }

    public void play() {
        if (owner.getCardsInHand().size()<4)
            return;
        owner.doForcedDiscard(1,true);
    }
}