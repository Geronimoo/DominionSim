package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class CharmCard extends DomCard {

    public CharmCard() {
      super( DomCardName.Charm);
    }

    @Override
    public void play() {
        if (owner.isHumanOrPossessedByHuman()){
            handleHuman();
            return;
        }
        if (owner.addingThisIncreasesBuyingPower(new DomCost(2,0))) {
            addMoney();
            return;
        }
        DomCardName theDesiredCard = owner.getDesiredCard(owner.getTotalPotentialCurrency(), false);
        if (theDesiredCard==null || owner.getDesiredCardWithRestriction(null,theDesiredCard.getCost(owner.getCurrentGame()),true,theDesiredCard)==null) {
            addMoney();
            return;
        }
        owner.addCharmReminder();
        if (DomEngine.haveToLog) DomEngine.addToLog( this + " will try to gain an extra card later in the turn (" + owner.getDesiredCardWithRestriction(null,theDesiredCard.getCost(owner.getCurrentGame()),true,theDesiredCard).toHTML() + ")");
    }

    private void handleHuman() {
        ArrayList<String> theOptions = new ArrayList<String>();
        theOptions.add("+$2, +Buy");
        theOptions.add("Gain an extra card");
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Select for Steward", theOptions, "Mandatory!");
        if (theChoice == 0)
            addMoney();
        if (theChoice == 1) {
            owner.addCharmReminder();
            if (DomEngine.haveToLog)
                DomEngine.addToLog(this + " will try to gain an extra card later in the turn");
        }
    }

    private void addMoney() {
        owner.addAvailableBuys(1);
        owner.addAvailableCoins(2);
    }

    @Override
    public double getPotentialCoinValue() {
        return 2;
    }
}