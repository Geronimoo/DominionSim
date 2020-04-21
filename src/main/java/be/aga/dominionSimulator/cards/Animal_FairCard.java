package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Animal_FairCard extends DomCard {
    public Animal_FairCard() {
      super( DomCardName.Animal_Fair);
    }

    public void play() {
      owner.addAvailableCoins(4);
      if (owner.getCurrentGame().countEmptyPiles()>0)
        owner.addAvailableBuys(owner.getCurrentGame().countEmptyPiles());
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }
}