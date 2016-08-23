package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class TriumphCard extends DomCard {
    public TriumphCard() {
      super( DomCardName.Triumph);
    }

    public void play() {
      DomCard theEstate = owner.getCurrentGame().takeFromSupply(DomCardName.Estate);
      if (theEstate==null) {
          if (DomEngine.haveToLog) DomEngine.addToLog(DomCardName.Estate.toHTML()+ " pile is empty");
          return;
      }
      owner.gain(theEstate);
      owner.addVP(owner.getCardsGainedLastTurn().size());
    }
}