package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class OutpostCard extends DomCard {
    public OutpostCard () {
      super( DomCardName.Outpost);
    }
    @Override
    public void play() {
        if (owner.getCurrentGame().getPreviousTurnTakenBy()!=owner) {
          owner.setExtraOutpostTurn(true);
        }
    }
    @Override
    public void resolveDuration() {
    	owner.setExtraOutpostTurn(false);
    }

    @Override
    public boolean wantsToBeMultiplied() {
        return false;
    }

    @Override
    public boolean wantsToBePlayed() {
        return !owner.hasExtraMissionTurn() && !owner.hasExtraOutpostTurn();
    }
}