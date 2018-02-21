package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class MiseryCard extends DomCard {
    public MiseryCard() {
      super( DomCardName.Misery);
    }

    public void play() {
        if (owner.isTwiceMiserable())
            return;
        if (owner.isMiserable())
          owner.setTwiceMiserable();
        else
          owner.setMiserable(true);
    }
}