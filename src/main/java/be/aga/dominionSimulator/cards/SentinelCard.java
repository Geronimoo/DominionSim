package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class SentinelCard extends DomCard {
    public SentinelCard() {
      super( DomCardName.Sentinel);
    }

    public void play() {
        ArrayList< DomCard > theTopCards = owner.revealTopCards( 5 );
        if (theTopCards.isEmpty())
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHumanPlayer(theTopCards);
        } else {
            Collections.sort(theTopCards, SORT_FOR_TRASHING);
            int count = 0;
            while (!theTopCards.isEmpty() && count < 2) {
                if (theTopCards.get(0).getTrashPriority() <= DomCardName.Copper.getTrashPriority()) {
                    owner.trash(theTopCards.remove(0));
                    count++;
                } else {
                    break;
                }
            }
            theTopCards.sort(SORT_FOR_PLAYING);
            for (DomCard card : theTopCards)
                owner.putOnTopOfDeck(card);
        }
    }

    private void handleHumanPlayer(ArrayList<DomCard> theTopCards) {
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("Choose up to 2 cards to trash" , theTopCards, theChosenCards, 0);
        while(theChosenCards.size()>2) {
            theChosenCards = new ArrayList<DomCard>();
            owner.getEngine().getGameFrame().askToSelectCards("Choose up to 2 cards to trash", theTopCards, theChosenCards, 0);
        }
        for (DomCard theCard: theChosenCards) {
            owner.trash(theTopCards.remove(theTopCards.indexOf(theCard)));
        }
        theTopCards.sort(SORT_FOR_PLAYING);
        for(DomCard card : theTopCards)
            owner.putOnTopOfDeck(card);
    }

    @Override
    public boolean wantsToBeMultiplied() {
        return false;
    }

    @Override
    public boolean wantsToBePlayed() {
        int theCount=0;
        for (DomCard theCard : owner.getDeck().getAllCards())
            theCount+=theCard.getTrashPriority()<16 ? 1 : 0;
        return theCount>2;
    }
}