package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Death_CartCard extends DomCard {
    public Death_CartCard () {
      super( DomCardName.Death_Cart);
    }

    public void play() {
      owner.addAvailableCoins(5);
      ArrayList<DomCard> theActions = owner.getCardsFromHand(DomCardType.Action);
      if (theActions.isEmpty()) {
        if (owner.getCardsInPlay().indexOf(this)!=-1)
    	  owner.trash(owner.removeCardFromPlay(this));
      } else {
    	Collections.sort(theActions,SORT_FOR_TRASHING);
    	owner.trash(owner.removeCardFromHand(theActions.get(0)));
      }
    }
    
    @Override
    public void doWhenGained() {
        owner.gain(DomCardName.Ruins);
        owner.gain(DomCardName.Ruins);
    }
}