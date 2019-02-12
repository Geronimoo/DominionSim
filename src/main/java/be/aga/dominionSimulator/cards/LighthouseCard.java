package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class LighthouseCard extends DomCard {
    public LighthouseCard () {
      super( DomCardName.Lighthouse);
    }

    public void play() {
        owner.addAvailableCoins(1);
        owner.addActions(1);
    }

    public void resolveDuration() {
      owner.addAvailableCoins(1);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}