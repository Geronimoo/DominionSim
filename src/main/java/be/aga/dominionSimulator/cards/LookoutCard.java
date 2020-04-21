package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class LookoutCard extends DomCard {
    public LookoutCard () {
      super( DomCardName.Lookout);
    }

    public void play() {
      owner.addActions( 1 );
      ArrayList< DomCard > theRevealedCards = owner.revealTopCards( 3 );
      if (theRevealedCards.isEmpty())
        return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman(theRevealedCards);
          return;
      }
      //first trash a card
      Collections.sort( theRevealedCards, SORT_FOR_TRASHING );
      owner.trash(theRevealedCards.remove( 0 ));
      if (theRevealedCards.isEmpty())
        return;
      //then discard a card
      Collections.sort( theRevealedCards, SORT_FOR_DISCARDING );
      owner.discard(theRevealedCards.remove( 0 ));
      //and finally, put the last card back on the deck
      if (!theRevealedCards.isEmpty()) {
        owner.putOnTopOfDeck(theRevealedCards.get( 0 ));
      }
    }

    private void handleHuman(ArrayList<DomCard> theRevealedCards) {
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        for (DomCard theCard : theRevealedCards) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
        for (DomCard theCard : theRevealedCards) {
            if (theCard.getName()==theChosenCard) {
                theRevealedCards.remove(theCard);
                owner.trash(theCard);
                break;
            }
        }
        if (theRevealedCards.isEmpty())
            return;
        theChooseFrom=new ArrayList<DomCardName>();
        for (DomCard theCard : theRevealedCards) {
            theChooseFrom.add(theCard.getName());
        }
        theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Discard a card", theChooseFrom, "Mandatory!");
        for (DomCard theCard : theRevealedCards) {
            if (theCard.getName()==theChosenCard) {
                theRevealedCards.remove(theCard);
                owner.discard(theCard);
                break;
            }
        }
        if (theRevealedCards.isEmpty())
            return;
        owner.putOnTopOfDeck(theRevealedCards.get(0));
    }

    @Override
    public boolean wantsToBePlayed() {
    	int theCount=0;
    	for (DomCardName card : owner.getDeck().keySet()){
    		if (card.getTrashPriority(owner)<16)
    			theCount+=owner.count(card);
    	}
        for (DomCard card : owner.getCardsInHand()){
            if (card.getTrashPriority()<16)
                theCount--;
        }
        return theCount*100/owner.countAllCards()>=15;
    }
}