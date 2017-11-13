package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class AnnexCard extends DomCard {
    public AnnexCard() {
      super( DomCardName.Annex);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        HashSet<DomCard> theCards = new HashSet<DomCard>();
        Collections.sort(owner.getDeck().getDiscardPile(),SORT_FOR_DISCARDING);
        for (DomCard theCard : owner.getDeck().getDiscardPile()){
            if (theCard.getDiscardPriority(1)>=DomCardName.Silver.getDiscardPriority(1))
                theCards.add(theCard);
        }
        for (DomCard card : theCards){
            owner.getDeck().getDiscardPile().remove(card);
            owner.putOnTopOfDeck(card);
        }
        while (owner.getDeck().getDiscardPile().size()>5)
            owner.putOnTopOfDeck(owner.getDeck().getDiscardPile().remove(owner.getDeck().getDiscardPile().size()-1));
        owner.shuffleDeck();
        owner.gain(DomCardName.Duchy);
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChosenCards = new ArrayList<DomCardName>();
        do {
            theChosenCards = new ArrayList<DomCardName>();
            owner.getEngine().getGameFrame().askToSelectCards("Choose and leave maximum 5", owner.getCardsFromDiscard(), theChosenCards, 0);
        } while(owner.getCardsFromDiscard().size()-theChosenCards.size()>5);
        for (DomCardName theCardName: theChosenCards) {
            owner.putOnTopOfDeck(owner.removeCardFromDiscard(theCardName));
        }
        owner.shuffleDeck();
        owner.gain(DomCardName.Duchy);
    }
}