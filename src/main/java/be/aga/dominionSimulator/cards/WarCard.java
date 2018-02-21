package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class WarCard extends DomCard {
    public WarCard() {
      super( DomCardName.War);
    }

    public void play() {
        ArrayList<DomCard> cardsToDiscard = new ArrayList<>();
        do {
            ArrayList<DomCard> theCards = owner.revealTopCards(1);
            if (theCards.isEmpty())
                break;
            DomCard card = theCards.get(0);
            if (card.getCoinCost(owner.getCurrentGame()) >= 3 && card.getCoinCost(owner.getCurrentGame()) <= 4 && card.getPotionCost() == 0 && card.getDebtCost() == 0) {
                owner.trash(card);
                owner.discard(cardsToDiscard);
                return;
            } else {
                cardsToDiscard.add(card);
            }
        } while (true);
        owner.discard(cardsToDiscard);
    }
}