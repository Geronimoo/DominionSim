package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;

public class CavalryCard extends DomCard {
    public CavalryCard() {
      super( DomCardName.Cavalry);
    }

    public void play() {
        DomCard theHorse = owner.getCurrentGame().takeFromSupply(DomCardName.Horse);
        if (theHorse!=null)
            owner.gain(theHorse);
        theHorse = owner.getCurrentGame().takeFromSupply(DomCardName.Horse);
        if (theHorse!=null)
            owner.gain(theHorse);
    }

    @Override
    public void doWhenGained() {
        owner.drawCards(2);
        owner.addAvailableBuys(1);
        if (owner.getCurrentGame().getActivePlayer()==owner) {
            if (owner.getPhase() == DomPhase.Buy_BuyStuff || owner.getPhase()==DomPhase.Buy_PlayTreasures) {
                //Guildhall and Merchant Guild add Coppers we can't use in the buy phase
                owner.handleDelayedCoffers();
                if (owner.isHumanOrPossessedByHuman()) {
                    owner.setPhase(DomPhase.Action);
                } else {
                    owner.triggerCavalry();
                }
            }
        }
    }
}