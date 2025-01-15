package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class TaskmasterCard extends DomCard {
    public TaskmasterCard() {
      super( DomCardName.Taskmaster);
    }

    public void play() {
      owner.addActions(1);
      owner.addAvailableCoins(1);
    }

    public void resolveDuration() {
      owner.addActions(1);
      owner.addAvailableCoins(1);
    }

    @Override
    public boolean discardAtCleanUp() {
        return !owner.hasGained$5ThisTurn();
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}