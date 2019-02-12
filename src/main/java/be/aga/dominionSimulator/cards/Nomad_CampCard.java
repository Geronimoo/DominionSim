package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Nomad_CampCard extends DomCard {
    public Nomad_CampCard () {
      super( DomCardName.Nomad_Camp);
    }

    public void play() {
      owner.addAvailableCoins(2);
      owner.addAvailableBuys(1);
    }
    //TODO gain on top of deck is handled in DomDeck, maybe handle here

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}