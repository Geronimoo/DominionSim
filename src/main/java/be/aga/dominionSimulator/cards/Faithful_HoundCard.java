package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPhase;

public class Faithful_HoundCard extends DomCard {
    public Faithful_HoundCard() {
      super( DomCardName.Faithful_Hound);
    }

    public void play() {
      owner.drawCards( 2 );
    }

    @Override
    public void doWhenDiscarded() {
        if (owner.getPhase()!= DomPhase.CleanUp) {
            owner.addFaithfulHoundToSetAside(owner.removeCardFromDiscard(this));
        } else {
            super.doWhenDiscarded();
        }
    }

    @Override
    public int getTrashPriority() {
        return DomCardName.Moat.getTrashPriority();
    }
}