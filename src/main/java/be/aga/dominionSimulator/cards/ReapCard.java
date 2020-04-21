package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class ReapCard extends DomCard {
    public ReapCard() {
      super( DomCardName.Reap);
    }

    public void play() {
        DomCard theCard = owner.getCurrentGame().takeFromSupply(DomCardName.Gold);
        if (theCard==null)
            return;
        owner.gain(theCard);
        if (owner.getTopOfDiscard()!=theCard) {
            if (DomEngine.haveToLog) DomEngine.addToLog( this + " has lost track of " + theCard +", so will not have an effect");
            return;
        }
        owner.setAsideToReap(owner.removeCardFromDiscard(theCard));
    }
}