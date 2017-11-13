package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class Horse_TradersCard extends DomCard {
    public Horse_TradersCard () {
      super( DomCardName.Horse_Traders);
    }

    public void play() {
      owner.addAvailableBuys(1);
      owner.addAvailableCoins(3);
      owner.doForcedDiscard(2, false);
    }

    @Override
    public boolean reactForHuman() {
        if (DomEngine.haveToLog)
            DomEngine.addToLog(owner + " sets a " + DomCardName.Horse_Traders.toHTML() + " aside");
        owner.horseTradersPile.add(owner.removeCardFromHand(this));
        return false;
    }
}