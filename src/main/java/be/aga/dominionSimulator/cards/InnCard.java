package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.HashSet;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class InnCard extends DomCard {
    public InnCard () {
      super( DomCardName.Inn);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(2);
      owner.doForcedDiscard(2, false);
    }
    
    @Override
    public void doWhenGained() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
    	HashSet<DomCard> theCards = new HashSet<DomCard>();
		for (DomCard theCard : owner.getDeck().getDiscardPile()){
			if (theCard.hasCardType(DomCardType.Action) && theCard.getDiscardPriority(1)>10)
			  theCards.add(theCard);
		}
		for (DomCard card : theCards){
		    owner.getDeck().removeFromDiscard(card);
		    owner.putOnTopOfDeck(card);
		}
		owner.shuffleDeck();
    }

    private void handleHuman() {
        ArrayList<DomCard> theChooseFrom = new ArrayList<DomCard>();
        for (DomCard theCard : owner.getDeck().getDiscardPile()){
            if (theCard.hasCardType(DomCardType.Action))
                theChooseFrom.add(theCard);
        }
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("Add to draw deck ?", theChooseFrom, theChosenCards, 0);
        for (int i = theChosenCards.size() - 1; i >= 0; i--) {
            for (DomCard theCard : theChooseFrom) {
                if (theChosenCards.get(i).getName() == theCard.getName()) {
                    owner.getDeck().removeFromDiscard(theCard);
                    owner.putOnTopOfDeck(theCard);
                    theChooseFrom.remove(theCard);
                    break;
                }
            }
        }
        owner.shuffleDeck();
    }
}