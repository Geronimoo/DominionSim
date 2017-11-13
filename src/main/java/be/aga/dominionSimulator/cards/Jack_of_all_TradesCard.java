package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Jack_of_all_TradesCard extends DrawUntilXCardsCard {
    public Jack_of_all_TradesCard () {
      super( DomCardName.Jack_of_all_Trades);
    }

    public void play() {
      gainSilver();
      spyOnYourself();
      drawUntil5CardsInHand();
      maybeTrashNonTreasureFromHand();
    }

	private void maybeTrashNonTreasureFromHand() {
		if (owner.getCardsInHand().isEmpty())
			  return;
		if (owner.isHumanOrPossessedByHuman()) {
			handleHuman();
			return;
		}
        Collections.sort( owner.getCardsInHand(), SORT_FOR_TRASHING);
	    DomCard theCardToTrash = findCardToTrash();
	    if (theCardToTrash==null){
	    	if (DomEngine.haveToLog) DomEngine.addToLog(owner + " trashes nothing");
		} else{
			owner.trash(owner.removeCardFromHand(theCardToTrash));
		}
	}

	private void handleHuman() {
    	owner.setNeedsToUpdate();
		ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
		for (DomCard theCard : owner.getCardsInHand()) {
			if (!theCard.hasCardType(DomCardType.Treasure))
			  theChooseFrom.add(theCard.getName());
		}
		if (theChooseFrom.isEmpty())
			return;
		DomCardName theCardToTrash = owner.getEngine().getGameFrame().askToSelectOneCard("Trash ?", theChooseFrom, "Don't trash");
		if (theCardToTrash!=null)
		  owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theCardToTrash).get(0)));
	}

	private DomCard findCardToTrash() {
	  DomCard theCardToTrash = null;
	  for (DomCard card : owner.getCardsInHand()){
		  if (card.getTrashPriority()<16 && !card.hasCardType(DomCardType.Treasure)){
			  theCardToTrash=card;
			  break;
		  }
	  }
	  return theCardToTrash;
	}

	private void drawUntil5CardsInHand() {
		owner.drawCards( 5 - owner.getCardsInHand().size() );
	}

	private void gainSilver() {
		if (owner.getCurrentGame().countInSupply(DomCardName.Silver)>0)
			  owner.gain(DomCardName.Silver);
	}

	private void spyOnYourself() {
	  ArrayList<DomCard> theRevealedCard = owner.revealTopCards(1);
	  if (theRevealedCard.isEmpty())
		  return;
	  if (owner.isHumanOrPossessedByHuman()) {
		  if (owner.getEngine().getGameFrame().askPlayer("<html>Discard " + theRevealedCard.get(0).getName().toHTML() +" ?</html>", "Resolving " + this.getName().toString())){
			  owner.discard(theRevealedCard.get(0));
		  } else {
			  owner.putOnTopOfDeck(theRevealedCard.get(0));
		  }
	  } else {
		  DomCard theCard = theRevealedCard.get(0);
		  if (theCard.getDiscardPriority(owner.getActionsLeft()) >= 16
				  || (owner.getCardsInHand().size() >= 5 && theCard.getDiscardPriority(1) >= 16)
				  || (findCardToTrash() == null
				  && theCard.getTrashPriority() < 16
				  && !theCard.hasCardType(DomCardType.Treasure))
				  || (findCardToTrash() != null
				  && findCardToTrash().getName() != DomCardName.Curse
				  && theCard.getName() == DomCardName.Curse)) {
			  owner.putOnTopOfDeck(theCard);
		  } else {
			  owner.discard(theCard);
		  }
	  }
	}
    @Override
    public int getPlayPriority() {
      return owner.getActionsLeft()>1 ? 6 : super.getPlayPriority();
    }

    @Override
    public boolean wantsToBePlayed() {
        if (owner.wants(DomCardName.Villa) && owner.getTotalAvailableCoins()>=4 && owner.getCardsFromHand(DomCardName.Jack_of_all_Trades).size()==1 && owner.getCardsInHand().size()>=4)
            return false;
        return true;
    }
}