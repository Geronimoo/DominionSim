package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class CourtyardCard extends DomCard {
    public CourtyardCard () {
      super( DomCardName.Courtyard);
    }

    public void play() {
    	owner.drawCards(3);
    	if (owner.getCardsInHand().isEmpty()) {
  	      if (DomEngine.haveToLog) 
            DomEngine.addToLog( owner + "'s hand is empty, so returns nothing");
          return;
    	}
        owner.putCardFromHandOnTop();
    }
    
    @Override
    public boolean wantsToBePlayed() {
      return !owner.isDeckEmpty();
    }

    @Override
    public int getPlayPriority() {
        return owner.getActionsLeft()>1 ? 10 : super.getPlayPriority();
    }
}