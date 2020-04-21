package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class GolemCard extends DomCard {
    public GolemCard () {
      super( DomCardName.Golem);
    }

    public void play() {
      ArrayList<DomCard> theRevealedActions = new ArrayList<DomCard>();
      ArrayList<DomCard> theCardsToDiscard = new ArrayList<DomCard>();
      while (theRevealedActions.size()<2 && owner.getDeckAndDiscardSize()>0) {
    	 ArrayList<DomCard> theCards = owner.revealUntilType(DomCardType.Action);
    	 for (DomCard theCard : theCards) {
    		 if (theCard.hasCardType(DomCardType.Action)&& theCard.getName()!=DomCardName.Golem) {
    			 theRevealedActions.add(theCards.remove(theCards.indexOf(theCard)));
    			 break;
    		 }
    	 }
		 theCardsToDiscard.addAll(theCards);
      }
      owner.discard(theCardsToDiscard);
      if (theRevealedActions.isEmpty())
    	  return;
      //adding 2 actions = fix to make sure the Golemed cards can be played
      owner.actionsLeft+=2;
      Collections.sort(theRevealedActions,SORT_FOR_PLAYING);
      if (theRevealedActions.size()>1 && owner.isHumanOrPossessedByHuman()) {
          handleHuman(theRevealedActions);
      }
      while (!theRevealedActions.isEmpty()) {
        owner.play(theRevealedActions.remove(0));
      }
      owner.actionsLeft-=2;
//      if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + theRevealedCards );
    }

    private void handleHuman(ArrayList<DomCard> theRevealedActions) {
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        do {
            owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u></html>", theRevealedActions, theChosenCards, 0);
        } while (theChosenCards.size() == 1);
        if (theChosenCards.size() == 2) {
            if (theRevealedActions.get(1).getName() == theChosenCards.get(0).getName()) {
                theRevealedActions.add(0, theRevealedActions.get(1));
                theRevealedActions.remove(2);
            }
        }
    }

    @Override
    public boolean wantsToBePlayed() {
        return owner.getDeckAndDiscardSize()>0;
    }
}