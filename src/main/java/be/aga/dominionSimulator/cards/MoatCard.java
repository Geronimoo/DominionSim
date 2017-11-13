package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class MoatCard extends DomCard {
    public MoatCard () {
      super( DomCardName.Moat);
    }

    public void play() {
      owner.drawCards( 2 );
    }

    @Override
    public boolean reactForHuman() {
        DomEngine.addToLog(this + " reveals a " + DomCardName.Moat.toHTML() + " from hand and prevents the attack");
        setReacted(true);
        return true;
    }
}