package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Merchant_ShipCard extends DomCard {
    public Merchant_ShipCard () {
      super( DomCardName.Merchant_Ship);
    }

    public void play() {
      owner.addAvailableCoins(2);
    }

    public void resolveDuration() {
      owner.addAvailableCoins(2);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}