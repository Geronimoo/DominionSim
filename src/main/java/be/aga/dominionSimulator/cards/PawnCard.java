package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class PawnCard extends DomCard {
    public PawnCard () {
      super( DomCardName.Pawn);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
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
      if (owner.getTotalPotentialCurrency().customCompare(new DomCost(7,0))>0) {
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

    private void handleHuman() {
        ArrayList<String> theOptions = new ArrayList<String>();
        theOptions.add("+Action/+Card");
        theOptions.add("+Action/+Coin");
        theOptions.add("+Action/+Buy");
        theOptions.add("+Coin/+Buy");
        theOptions.add("+Coin/+Card");
        theOptions.add("+Card/+Buy");
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Select for Pawn", theOptions, "Mandatory!");
        if (theChoice==0) {
            owner.addActions(1);
            owner.drawCards(1);
        }
        if (theChoice==1) {
            owner.addActions(1);
            owner.addAvailableCoins(1);
        }
        if (theChoice==2) {
            owner.addActions(1);
            owner.addAvailableBuys(1);
        }
        if (theChoice==3) {
            owner.addAvailableCoins(1);
            owner.addAvailableBuys(1);
        }
        if (theChoice==4) {
            owner.addAvailableCoins(1);
            owner.drawCards(1);
        }
        if (theChoice==5) {
            owner.addAvailableBuys(1);
            owner.drawCards(1);
        }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}