package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Caravan_GuardCard extends DomCard {
    public Caravan_GuardCard() {
      super( DomCardName.Caravan_Guard);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
    }

    public void resolveDuration() {
      owner.addAvailableCoins(1);
    }

    @Override
    public boolean reactForHuman() {
        owner.play(owner.removeCardFromHand(this));
        return false;
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}