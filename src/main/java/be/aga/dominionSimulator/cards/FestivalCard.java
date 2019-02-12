package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class FestivalCard extends DomCard {
    public FestivalCard () {
      super( DomCardName.Festival);
    }

    public void play() {
      owner.addActions(2);
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