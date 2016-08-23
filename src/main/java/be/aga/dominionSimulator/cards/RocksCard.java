package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPhase;

public class RocksCard extends DomCard {

    public RocksCard() {
      super( DomCardName.Rocks);
    }
    
    @Override
    public void play() {
      owner.addAvailableCoins(1);
    }

    @Override
    public void doWhenGained() {
        DomCard theSilver = owner.getCurrentGame().takeFromSupply(DomCardName.Silver);
        if (theSilver==null)
            return;
        if (owner.getPhase()==DomPhase.Buy) {
            owner.gainOnTopOfDeck(theSilver);
        } else {
            owner.gainInHand(theSilver);
        }
    }

    @Override
    public void doWhenTrashed() {
        doWhenGained();
    }
}