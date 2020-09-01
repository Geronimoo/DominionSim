package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class VaultCard extends DomCard {
    public VaultCard () {
      super( DomCardName.Vault);
    }

    public void play() {
      owner.drawCards( 2 );
      if (owner.isHumanOrPossessedByHuman()) {
          ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
          owner.getEngine().getGameFrame().askToSelectCards("<html>Discard for +$1</html>" , owner.getCardsInHand(), theChosenCards, 0);
          for (DomCard theCard : theChosenCards) {
             owner.discardFromHand(theCard.getName());
             owner.addAvailableCoins(1);
          }
      } else {
          if (owner.getActionsAndVillagersLeft() > 0 && !owner.getCardsFromHand(DomCardName.Tactician).isEmpty() && owner.getCardsInHand().size() >= 2) {
              handleTactician();
          } else {
              playNormal();
          }
      }
      handleOpponents();
    }

	private void playNormal() {
      Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
      DomCard theCardToDiscard = null;
      while (!owner.getCardsInHand().isEmpty()){
	    theCardToDiscard = owner.getCardsInHand().get(0);
	    if (theCardToDiscard.getDiscardPriority(owner.getActionsAndVillagersLeft()) >= 20)
	      return;
        owner.discardFromHand(theCardToDiscard);
	    owner.addAvailableCoins( 1 );
	  } 
	}

	private void handleOpponents() {
      for (DomPlayer thePlayer : owner.getOpponents()) {
        if (thePlayer.isHumanOrPossessedByHuman()) {
            ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
            do {
                theChosenCards = new ArrayList<DomCard>();
                owner.getEngine().getGameFrame().askToSelectCards("Discard 2?", thePlayer.getCardsInHand(), theChosenCards, 0);
            } while (!(theChosenCards.size()==2 || theChosenCards.size()==0));
            if (theChosenCards.size()==2) {
                for (DomCard theCard : theChosenCards) {
                    thePlayer.discardFromHand(theCard.getName());
                }
                thePlayer.drawCards(1);
            }
            continue;
        }
    	if (!thePlayer.getCardsFromHand(DomCardName.Vault).isEmpty()) {
    	  //when the opponent has a Vault in hand he will in most cases prefer to keep more cards in hand
    	  if (DomEngine.haveToLog) DomEngine.addToLog( thePlayer + " discards nothing!" );
    	  continue;
    	}
    	if (!thePlayer.getCardsFromHand(DomCardName.Trading_Post).isEmpty()) {
          //when the opponent has a Trading Post in hand he will in most cases prefer to keep his garbage in hand
      	  if (DomEngine.haveToLog) DomEngine.addToLog( thePlayer + " discards nothing!" );
    	  continue;
      	}
        Collections.sort(thePlayer.getCardsInHand(), SORT_FOR_DISCARDING);
        int theDiscardCount=0;
        for (DomCard theCard : thePlayer.getCardsInHand()) {
          theDiscardCount+= theCard.getDiscardPriority(1) < 15 ? 1 : 0;
        }
        if (theDiscardCount>=2) {
          thePlayer.discardFromHand(thePlayer.getCardsInHand().get(0));
          thePlayer.discardFromHand(thePlayer.getCardsInHand().get(0));
          thePlayer.drawCards( 1 );
        } else {
          if (DomEngine.haveToLog) DomEngine.addToLog( thePlayer + " discards nothing!" );
        }
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

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}