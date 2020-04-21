package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Abandoned_MineCard extends DomCard {

    public Abandoned_MineCard () {
      super( DomCardName.Abandoned_Mine);
    }
    
    @Override
    public void play() {
      owner.addAvailableCoins(1);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

    @Override
    public boolean wantsToBePlayed() {
        return !owner.wants(DomCardName.Animal_Fair);
    }
}