package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class BishopCard extends DomCard {
    public BishopCard () {
      super( DomCardName.Bishop);
    }

    public void play() {
      owner.addAvailableCoins( 1 );
      owner.addVP( 1);
      DomCard theCardToTrash = null;
      if (!owner.getCardsInHand().isEmpty()) {
        theCardToTrash = findCardToTrash();
        if (theCardToTrash==null) {
          //this is needed when card is played with Throne Room effect
          Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
          theCardToTrash=owner.getCardsInHand().get(0);
        }
        owner.trash(owner.removeCardFromHand( theCardToTrash ));
        if (theCardToTrash.getCost(owner.getCurrentGame()).getCoins()>0)
          owner.addVP(theCardToTrash.getCost(owner.getCurrentGame()).getCoins()/2);
      }
      handleOpponents();
    }

	private DomCard findCardToTrash() {
      Collections.sort( owner.getCardsInHand(), SORT_FOR_TRASHING);
      DomCard theCardToTrash = owner.getCardsInHand().get( 0 );
      if (theCardToTrash==this && owner.getCardsInHand().size()>1)
    	  theCardToTrash = owner.getCardsInHand().get( 1 );
      if (owner.countAllCards()<10) {
      	//this is to enable the 5-card deck of Bishop, 2 Silvers, a Gold and a Province
      	ArrayList<DomCard> theProvinces = owner.getCardsFromHand(DomCardName.Province);
      	if (!theProvinces.isEmpty()) {
      		theCardToTrash=theProvinces.get(0);
      	}
      }
      if (!owner.getCardsFromHand(DomCardName.Market_Square).isEmpty() && !owner.getCardsFromHand(DomCardName.Gold).isEmpty())
          theCardToTrash=owner.getCardsFromHand(DomCardName.Gold).get(0);
      return theCardToTrash;
	}

	private void handleOpponents() {
		for (DomPlayer thePlayer : owner.getOpponents()) {
		    boolean trashes=false;
		    if (thePlayer.getCardsInHand().size()>0) {
		      Collections.sort( thePlayer.getCardsInHand() , SORT_FOR_TRASHING);
		      DomCard theCardToTrash = thePlayer.getCardsInHand().get( 0 );
		      if (theCardToTrash.getTrashPriority()<16) {
		        if (!thePlayer.removingReducesBuyingPower( theCardToTrash )){
		          thePlayer.trash(thePlayer.removeCardFromHand( theCardToTrash));
		          trashes=true;
		        }
		      }
		    }
		    if (DomEngine.haveToLog && !trashes) DomEngine.addToLog( thePlayer + " trashes nothing");
		  }
	}

	@Override
	public boolean wantsToBePlayed() {
		if (owner.getTotalMoneyInDeck()<7 && owner.countAllCards()<5)
		  //little fix to prevent The Golden deck from trashing itself to death
		  return false;
		return super.wantsToBePlayed();
	}
}