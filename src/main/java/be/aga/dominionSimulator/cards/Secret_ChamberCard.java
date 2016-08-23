package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Secret_ChamberCard extends DomCard {
    public Secret_ChamberCard () {
      super( DomCardName.Secret_Chamber);
    }

    public void play() {
      if (owner.getActionsLeft()>0 
      && !owner.getCardsFromHand(DomCardName.Tactician).isEmpty() 
      && owner.getCardsInHand().size()>=2) {
    	handleTactician();
      } else {
        playNormal();
      }
    }

	private void playNormal() {
		if (owner.getCardsInHand().isEmpty())
			return;
		  Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
		  DomCard theCardToDiscard = null;
		  do {
		    theCardToDiscard = owner.getCardsInHand().get(0);
		    if (theCardToDiscard.getDiscardPriority(owner.getActionsLeft()) >= 16)
		      return;
		    owner.discardFromHand(theCardToDiscard);
		    owner.addAvailableCoins( 1 );
		  } while (!owner.getCardsInHand().isEmpty());
	}

    public void react() {
       if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + this );
       owner.drawCards(2);
       for (DomPlayer player : owner.getOpponents()){
    	   if (!player.getCardsFromPlay(DomCardName.Mountebank).isEmpty()){
    		   reactForMountebank();
    		   return;
    	   }
    	   if (!player.getCardsFromPlay(DomCardName.Pirate_Ship).isEmpty()){
    		   reactForPirate_Ship();
    		   return;
    	   }
    	   //TODO Rabble
    	   //TODO Saboteur
    	   //TODO Thief
    	   //TODO Militia/Goons/Ghost Ship/...
    	   //TODO Menagerie in hand!
       }
       owner.doForcedDiscard(2,true);
    }

	private void reactForPirate_Ship() {
		if (owner.getCardsFromHand(DomCardType.Treasure).size() > owner.getCardsInHand().size()-2){
		  owner.doForcedDiscard(2,true);
		  return;
		}
		ArrayList<DomCard> theTreasures = new ArrayList<DomCard>();
		//put all treasures aside then put the two worst cards back on deck
		for (DomCard card : owner.getCardsFromHand(DomCardType.Treasure)){
		  theTreasures.add(owner.removeCardFromHand(card));
		}
		owner.doForcedDiscard(2,true);
		owner.getCardsInHand().addAll(theTreasures);
	}

	private void reactForMountebank() {
  	  if (!owner.getCardsFromHand(DomCardName.Curse).isEmpty()) {
		//put curse aside and put it back in hand after discarding so we can discard the curse to Mountebank
  		DomCard theCurse = owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Curse).get(0));
		owner.doForcedDiscard(2,true);
		owner.getCardsInHand().add( theCurse);
	  } else {
	    owner.doForcedDiscard(2,true);
	  }
	}

	private void handleTactician() {
	  //first set aside Tactician and 1 card to trigger the Tactician
      DomCard theTactician = owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Tactician).get(0));
      Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
      DomCard theCardToTriggerTactician = owner.removeCardFromHand(owner.getCardsInHand().get(0));
      //then discard everything
	  while (!owner.getCardsInHand().isEmpty()){
        owner.discardFromHand(owner.getCardsInHand().get(0));
	    owner.addAvailableCoins( 1 );
	  }
	  //then return theTactician and 1 card to the hand
	  owner.getCardsInHand().add(theTactician);
	  owner.getCardsInHand().add(theCardToTriggerTactician);
    }

}