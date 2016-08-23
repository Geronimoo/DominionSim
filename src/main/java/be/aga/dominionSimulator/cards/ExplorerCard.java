package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class ExplorerCard extends DomCard {
    public ExplorerCard () {
      super( DomCardName.Explorer);
    }

    public void play() {
    	if (owner.getCardsFromHand(DomCardName.Province).isEmpty()) {
          owner.gainInHand(DomCardName.Silver);
    	} else {
          if (DomEngine.haveToLog) 
            DomEngine.addToLog( owner + " reveals a Province");
          owner.gainInHand(DomCardName.Gold);
    	}
    }
}