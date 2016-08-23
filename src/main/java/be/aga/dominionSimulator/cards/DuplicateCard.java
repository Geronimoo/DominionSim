package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class DuplicateCard extends DomCard {
    public DuplicateCard() {
      super( DomCardName.Duplicate);
    }

    public void play() {
      owner.putOnTavernMat(owner.removeCardFromPlay(this));
    }

    @Override
    public void doWhenCalled() {
        super.doWhenCalled();
    }

    public boolean wantsToGain(DomCard aCard) {
        if (owner.getCurrentGame().countInSupply(aCard.getName())==0)
            return false;
        if (owner.getPlayStrategyFor(this)== DomPlayStrategy.dukeEnabler) {
            if (aCard.getName()==DomCardName.Duchy ||aCard.getName()==DomCardName.Duke)
                return true;
            return false;
        }
        if (aCard.getCost(owner.getCurrentGame()).compareTo(new DomCost(6,0))<=0 && owner.wants(aCard.getName()))
          return true;

        return false;
    }
}