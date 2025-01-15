package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class UberDominationCard extends DomCard {
    public UberDominationCard() {
        super(DomCardName.UberDomination);
    }

    public void play() {
        owner.setUberDominationWinTrigger();
        owner.addDebt(121);
    }
}