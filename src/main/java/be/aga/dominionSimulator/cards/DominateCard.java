package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class DominateCard extends DomCard {
    public DominateCard() {
      super( DomCardName.Dominate);
    }

    public void play() {
      DomCard theProvince = owner.getCurrentGame().takeFromSupply(DomCardName.Province);
      if (theProvince==null) {
          if (DomEngine.haveToLog) DomEngine.addToLog(DomCardName.Province.toHTML()+ " pile is empty");
          return;
      }
      owner.gain(theProvince);
      owner.addVP(9);
    }
}