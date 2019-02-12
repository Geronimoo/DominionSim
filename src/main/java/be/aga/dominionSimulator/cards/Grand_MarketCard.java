package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Grand_MarketCard extends DomCard {
    public Grand_MarketCard () {
      super( DomCardName.Grand_Market);
    }

    public void play() {
      owner.addActions(1);
      owner.addAvailableCoins(2);
      owner.addAvailableBuys(1);
      owner.drawCards(1);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}