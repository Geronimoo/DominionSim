package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class PirateCard extends DomCard {
    public PirateCard() {
      super( DomCardName.Pirate);
    }

    @Override
    public void resolveDuration() {
        DomCardName desiredTreasure = owner.getDesiredCard(DomCardType.Treasure, new DomCost(6, 0), false, false, null);
        owner.gainInHand(desiredTreasure);
    }
}