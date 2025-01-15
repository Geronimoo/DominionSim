package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Mining_RoadCard extends DomCard {
    public Mining_RoadCard() {
      super( DomCardName.Mining_Road);
    }

    public void play() {
      owner.addActions(1);
      owner.addAvailableCoins(2);
      owner.addAvailableBuys(1);
      owner.addMiningRoadTrigger();
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }
}