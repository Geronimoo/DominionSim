package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class IronmongerCard extends DomCard {
    public IronmongerCard() {
      super( DomCardName.Ironmonger);
    }

    public void play() {
        boolean willdraw = false;
        owner.addActions(1);
        owner.drawCards(1);
        ArrayList<DomCard> theTopCards = owner.revealTopCards(1);
        if (theTopCards.isEmpty())
            return;
        DomCard theTopCard = theTopCards.get(0);
        if (theTopCard.hasCardType(DomCardType.Treasure))
            owner.addAvailableCoins(1);
        if (theTopCard.hasCardType(DomCardType.Victory))
            willdraw=true;
        if (theTopCard.hasCardType(DomCardType.Action))
            owner.addActions(1);
        if (theTopCard.getDiscardPriority(owner.getActionsLeft())>=16)
            owner.putOnTopOfDeck(theTopCard);
        else
            owner.discard(theTopCard);
        if (willdraw)
            owner.drawCards(1);
    }
}