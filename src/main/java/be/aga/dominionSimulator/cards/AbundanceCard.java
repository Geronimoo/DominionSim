package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class AbundanceCard extends DomCard {
    private boolean hasTriggered = false;

    public AbundanceCard() {
      super( DomCardName.Abundance);
    }

    public boolean hasTriggered() {
        return hasTriggered;
    }

    public void trigger() {
        if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " triggers " + this.getName().toHTML());
        owner.addAvailableCoins(3);
        owner.addAvailableBuys(1);
        this.hasTriggered = true;
    }

    @Override
    public boolean discardAtCleanUp() {
        if (hasTriggered) {
            hasTriggered=false;
            return true;
        }
        return false;
    }

    @Override
    public void cleanVariablesFromPreviousGames() {
        super.cleanVariablesFromPreviousGames();
        hasTriggered=false;
    }
}