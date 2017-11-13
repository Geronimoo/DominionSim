package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class NoblesCard extends DomCard {
    public NoblesCard () {
      super( DomCardName.Nobles);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      if (owner.isInReserve(DomCardName.Coin_of_the_Realm) && owner.getDeckSize()>0) {
          owner.drawCards(3);
          return;
      }
      if ( owner.getDeckSize()==0 || (owner.getNextActionToPlay()!=null && owner.getActionsLeft()==0)) {
    	  owner.addActions(2);
      } else {
    	  owner.drawCards(3);
      }
    }

    private void handleHuman() {
        ArrayList<String> theOptions = new ArrayList<String>();
        theOptions.add("+3 Cards");
        theOptions.add("+2 Actions");
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Minion", theOptions, "Mandatory!");
        if (theChoice==1) {
            owner.addActions(2);
        } else {
            owner.drawCards(3);
        }

    }
}