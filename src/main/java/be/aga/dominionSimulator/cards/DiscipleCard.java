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
        DomCard theCardToMultiply = getCardToMultiply();
        if (theCardToMultiply==null)
            return;
        super.play();
        if (theCardToMultiply.hasCardType(DomCardType.Kingdom))
          owner.gain(theCardToMultiply.getName());
    }

    @Override
    public void handleCleanUpPhase() {
        if (owner==null)
            return;
        if (owner.wants(DomCardName.Teacher) && areDurationsEmpty()) {
            DomPlayer theOwner = owner;
            owner.returnToSupply(this);
            theOwner.gain(DomCardName.Teacher);
            return;
        }
        super.handleCleanUpPhase();
    }

}