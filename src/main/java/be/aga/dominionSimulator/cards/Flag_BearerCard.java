package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomArtifact;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Flag_BearerCard extends DomCard {
    public Flag_BearerCard() {
      super( DomCardName.Flag_Bearer);
    }

    public void play() {
      owner.addAvailableCoins(2);
    }

    @Override
    public void doWhenGained() {
        owner.getCurrentGame().giveArtifactTo(DomArtifact.Flag, owner);
    }

    @Override
    public void doWhenTrashed() {
        owner.getCurrentGame().giveArtifactTo(DomArtifact.Flag, owner);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}