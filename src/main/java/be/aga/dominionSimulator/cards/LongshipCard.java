package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class LongshipCard extends DomCard {
    public LongshipCard() {
      super( DomCardName.Longship);
    }

    public void play() {
        owner.addActions(2);
    }

    @Override
    public void resolveDuration() {
        owner.drawCards(2);
    }
}