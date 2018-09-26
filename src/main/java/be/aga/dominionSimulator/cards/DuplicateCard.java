package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class DuplicateCard extends DomCard {
    public DuplicateCard() {
      super( DomCardName.Duplicate);
    }

    public void play() {
        if (owner.getCardsFromPlay(getName()).contains(this))
          owner.putOnTavernMat(owner.removeCardFromPlay(this));
    }

    @Override
    public void doWhenCalled() {
        super.doWhenCalled();
    }

    public boolean wantsToGain(DomCard aCard) {
        if (owner.isHumanOrPossessedByHuman() &&
            new DomCost(6, 0).customCompare(aCard.getCost(owner.getCurrentGame()))>=0) {
            owner.setNeedsToUpdateGUI();
            return owner.getEngine().getGameFrame().askPlayer("<html>Duplicate " + aCard.getName().toHTML() +" ?</html>", "Resolving " + this.getName().toString());
        } else {
            if (owner.getCurrentGame().countInSupply(aCard.getName()) == 0)
                return false;
            if (owner.getPlayStrategyFor(this) == DomPlayStrategy.dukeEnabler) {
                if (aCard.getName() == DomCardName.Duchy || aCard.getName() == DomCardName.Duke)
                    return true;
                return false;
            }
            if (new DomCost(6, 0).customCompare(aCard.getCost(owner.getCurrentGame()))>=0 && owner.wants(aCard.getName()) && aCard.getTrashPriority() > DomCardName.Silver.getTrashPriority())
                return true;
        }
        return false;
    }
}