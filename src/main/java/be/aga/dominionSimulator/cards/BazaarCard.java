package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class BazaarCard extends DomCard {
    public BazaarCard () {
      super( DomCardName.Bazaar);
    }

    public void play() {
      owner.addActions(2);
      owner.addAvailableCoins(1);
      owner.drawCards(1);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}