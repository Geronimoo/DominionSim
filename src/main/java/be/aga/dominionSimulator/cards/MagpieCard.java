package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class MagpieCard extends DomCard {
    public MagpieCard() {
      super( DomCardName.Magpie);
    }

    public void play() {
        owner.addActions(1);
        owner.drawCards(1);
        ArrayList<DomCard> theTopCards = owner.revealTopCards(1);
        if (theTopCards.isEmpty())
            return;
        DomCard theTopCard = theTopCards.get(0);
        if (theTopCard.hasCardType(DomCardType.Treasure))
            owner.putInHand(theTopCards.remove(0));
        if (theTopCard.hasCardType(DomCardType.Victory) || theTopCard.hasCardType(DomCardType.Action))
            owner.gain(DomCardName.Magpie);
        if (!theTopCards.isEmpty())
            owner.putOnTopOfDeck(theTopCard);
    }
}