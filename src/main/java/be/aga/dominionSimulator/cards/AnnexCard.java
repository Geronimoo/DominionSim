package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.Collections;
import java.util.HashSet;

public class AnnexCard extends DomCard {
    public AnnexCard() {
      super( DomCardName.Annex);
    }

    public void play() {
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
}