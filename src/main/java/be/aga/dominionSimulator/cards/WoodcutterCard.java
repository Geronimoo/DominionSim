package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class WoodcutterCard extends DomCard {
    public WoodcutterCard () {
      super( DomCardName.Woodcutter);
    }

    public void play() {
        owner.addAvailableCoins(2);
        owner.addAvailableBuys(1);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}