package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class DesperationCard extends DomCard {

    public DesperationCard() {
        super(DomCardName.Desperation);
    }

    public void play() {
        if (owner.isDesperationActivated() || owner.getCurrentGame().countInSupply(DomCardName.Curse)==0)
            return;
        owner.gain(DomCardName.Curse);
        owner.addAvailableBuys(1);
        owner.addAvailableCoins(2);
        owner.setDesperationActivated();
    }
}