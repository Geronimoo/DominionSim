package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class DiademCard extends DomCard {

    public DiademCard () {
      super( DomCardName.Diadem);
    }
    
    public void play() {
      if (DomEngine.haveToLog) 
    	  DomEngine.addToLog( owner + " has " + owner.getActionsLeft() + " unused actions left");
      owner.addAvailableCoins(2+owner.getActionsLeft());
    }
}