package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class GuardianCard extends DomCard {
    public GuardianCard() {
      super( DomCardName.Guardian);
    }

    public void resolveDuration() {
      owner.addAvailableCoins(1);
    }

    @Override
    public void doWhenGained() {
        owner.addCardToHand(this);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}