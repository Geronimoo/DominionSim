package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class PoacherCard extends DomCard {
    public PoacherCard() {
      super( DomCardName.Poacher);
    }

    public void play() {
      owner.addActions(1);
      owner.addAvailableCoins(1);
      owner.drawCards(1);
      if (owner.isHumanOrPossessedByHuman())
          owner.setNeedsToUpdateGUI();
      owner.doForcedDiscard(owner.getCurrentGame().countEmptyPiles(),false);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }


}