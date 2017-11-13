package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class DiscipleCard extends MultiplicationCard {
    public DiscipleCard() {
      super( DomCardName.Disciple);
    }

    public void play() {
        super.play();
        if (myCardToMultiply!=null && myCardToMultiply.hasCardType(DomCardType.Kingdom))
          owner.gain(myCardToMultiply.getName());
    }

    @Override
    public void handleCleanUpPhase() {
        if (owner==null || !areDurationsEmpty())
            return;
        if ((!owner.isHumanOrPossessedByHuman() && owner.wants(DomCardName.Teacher))
                || (owner.isHumanOrPossessedByHuman()
                && owner.getCurrentGame().countInSupply(DomCardName.Teacher)>0
                && owner.getEngine().getGameFrame().askPlayer("<html>Exchange "+ this.getName()+" for " + DomCardName.Teacher.toHTML() +"</html>", "Resolving " + this.getName().toString()))){
            DomPlayer theOwner = owner;
            owner.returnToSupply(this);
            DomCard theTraveller = theOwner.getCurrentGame().takeFromSupply(DomCardName.Teacher);
            theOwner.getDeck().addPhysicalCardWhenNotGained(theTraveller);
            theOwner.getDeck().justAddToDiscard(theTraveller);
            return;
        }
        super.handleCleanUpPhase();
    }
}