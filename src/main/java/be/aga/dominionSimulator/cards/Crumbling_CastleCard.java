package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Crumbling_CastleCard extends DomCard {
    public Crumbling_CastleCard() {
      super( DomCardName.Crumbling_Castle);
    }

    @Override
    public void doWhenTrashed() {
        owner.addVP(1);
        owner.gain(DomCardName.Silver);
    }

    @Override
    public void doWhenGained() {
        owner.addVP(1);
        owner.gain(DomCardName.Silver);
    }
}