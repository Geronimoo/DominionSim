package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class Native_VillageCard extends DomCard {
    public Native_VillageCard () {
      super( DomCardName.Native_Village);
    }

    public void play() {
      owner.addActions(2);
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      switch (owner.getPlayStrategyFor(this)) {
        case standard:
          playDefault();
          break;
        case bigTurnBridge:
        case bigTurnGoons:
          playForBigTurn();
          break;
        case ApothecaryNativeVillage:
        	//always put card away because it's a useless green
        	playNativeVillageForStorage();
		default:
			break;
      }    
    }

    private void handleHuman() {
        ArrayList<String> theOptions = new ArrayList<String>();
        theOptions.add("Set aside");
        theOptions.add("<html>Pick up: " + owner.getEngine().getCurrentGame().getActivePlayer().getNativeVillageMatToString());
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Native Village", theOptions, "Mandatory!");
        if (theChoice == 0) {
            playNativeVillageForStorage();
        } else {
            playNativeVillageForCards();
        }
    }

    private void playForBigTurn() {
      if (owner.getCardsFromHand( DomCardName.Native_Village ).size() > 0 
       || owner.getNativeVillageMat().isEmpty()
       || !isBigTurnReady()) {
        playNativeVillageForStorage();
      } else {
        playNativeVillageForCards();
      }
    }

    private boolean isBigTurnReady() {
        switch ( owner.getPlayStrategyFor( this ) ) {
        case bigTurnBridge :
            for (DomPlayer thePlayer : owner.getOpponents()) {
              if (thePlayer.countVictoryPoints()-owner.countVictoryPoints()>24) {
                return true;
              }
            }
            int theBridgeCount=owner.getCardsFromHand(DomCardName.Bridge).size();
            int theVillageCount=owner.getCardsFromHand(DomCardName.Native_Village).size()+owner.getActionsAndVillagersLeft();
            for (DomCard card: owner.getNativeVillageMat()) {
                theBridgeCount += card.getName()==DomCardName.Bridge ? 1 : 0;
                theVillageCount += card.getName() == DomCardName.Native_Village ? 1:0;
            }
            return theBridgeCount>5 && theVillageCount>5;
//            break;

        case bigTurnGoons:
            if (owner.count(DomCardName.Goons)>3) {
              return true;
            }
            break;

        default :
            break;
        }
        return false;
    }

    private void playDefault() {
      if (owner.addingThisIncreasesBuyingPower( owner.getPotentialCurrencyFromNativeVillageMat() )) {
        playNativeVillageForCards();
      } else {
        playNativeVillageForStorage();
      } 
    }

      public void playNativeVillageForStorage() {
        ArrayList< DomCard > theCard = owner.revealTopCards( 1 );
        if (!theCard.isEmpty()) {
          owner.getNativeVillageMat().addAll( theCard );
          if (DomEngine.haveToLog) DomEngine.addToLog( owner + " adds a " + theCard.get( 0 ) +" to his Village Mat");
          if (DomEngine.haveToLog) DomEngine.addToLog( owner + "'s Village Mat contains: " + owner.getNativeVillageMat());
        } else {
          if (DomEngine.haveToLog) DomEngine.addToLog( owner + " adds nothing to his Village Mat");
        }
      }

      public void playNativeVillageForCards() {
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " puts all cards from the Village Mat into his hand");
        owner.getCardsInHand().addAll( owner.getNativeVillageMat());
        owner.getNativeVillageMat().clear();
        owner.showHand();
      }
      @Override
    public boolean wantsToBePlayed() {
    	if (owner.getPlayStrategyFor(this)==DomPlayStrategy.ApothecaryNativeVillage){
    		if (owner.getKnownTopCards()>0
    		  && owner.getDeck().lookAtTopCard().getDiscardPriority(1)<DomCardName.Copper.getDiscardPriority(1))
    			return true;
    		return false;
    	}
    	return true;
    }
}