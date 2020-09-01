package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

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
      if (owner.isInReserve(DomCardName.Coin_of_the_Realm) && owner.getDeckAndDiscardSize()>0) {
          owner.drawCards(3);
          return;
      }
      if ( owner.getDeckAndDiscardSize()==0 || (owner.getNextActionToPlay()!=null && owner.getActionsAndVillagersLeft()==0)) {
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

    @Override
    public int getPlayPriority() {
        if (owner.getCardsFromHand(DomCardType.Action).size()-owner.getCardsFromHand(DomCardName.Nobles).size()>owner.getCardsFromHand(DomCardType.Terminal).size())
            return 35;
        return super.getPlayPriority();
    }
}