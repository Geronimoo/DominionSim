package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class CharmCard extends DomCard {

    public CharmCard() {
      super( DomCardName.Charm);
    }

    @Override
    public void play() {
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

    private void addMoney() {
        owner.addAvailableBuys(1);
        owner.addAvailableCoins(2);
    }

    @Override
    public double getPotentialCoinValue() {
        return 2;
    }
}