package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

public class PawnCard extends DomCard {
    public PawnCard () {
      super( DomCardName.Pawn);
    }

    public void play() {
      int theChoicesCount=0;
      boolean coinsChosen=false;
      boolean actionChosen=false;
      boolean buysChosen = false;

      //special handling for Library decks
      if (owner.countInDeck(DomCardName.Library)>0 ) {
          if (owner.getCardsFromHand(DomCardName.Library).isEmpty()) {
              owner.addActions(1);
              owner.drawCards(1);
              return;
          }
          owner.addActions(1);
          owner.addAvailableCoins(1);
          return;
      }

      //standard handling
      if (owner.getNextActionToPlay()!=null && owner.getActionsLeft()==0) {
    	  owner.addActions(1);
    	  theChoicesCount++;
    	  actionChosen=true;
      }
      if (owner.getTotalPotentialCurrency().compareTo(new DomCost(7,0))>0) {
            owner.addAvailableBuys(1);
            theChoicesCount++;
            buysChosen=true;
      }
      if (owner.addingThisIncreasesBuyingPower( new DomCost( 1,0 ))) {
    	  owner.addAvailableCoins(1);
    	  theChoicesCount++;
    	  coinsChosen=true;
      }
      if (owner.getDeckSize()>0 && theChoicesCount<2){
    	  owner.drawCards(1);
    	  theChoicesCount++;
      }
      if (theChoicesCount<2 && !buysChosen) {
    	  owner.addAvailableBuys(1);
    	  theChoicesCount++;
      }
      if (theChoicesCount<2 && !coinsChosen){
    	  owner.addAvailableCoins(1);
          theChoicesCount++;
      }
      if (theChoicesCount<2 && !actionChosen){
    	  owner.addActions(1);
          theChoicesCount++;
      }
    }
}