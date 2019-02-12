package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class PeasantCard extends TravellerCard {
    public PeasantCard() {
      super( DomCardName.Peasant);
      myUpgrade=DomCardName.Soldier;
    }

    public void play() {
      owner.addAvailableCoins(1);
      owner.addAvailableBuys(1);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}