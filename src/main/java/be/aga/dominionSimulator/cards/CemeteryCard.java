package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class CemeteryCard extends DomCard {
    public CemeteryCard() {
      super( DomCardName.Cemetery);
    }

    @Override
    public void doWhenGained() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHumanPlayer();
        } else {
            int theMin$Indeck = 6;
            Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
            for (int i = 0; i < 4 && !owner.getCardsInHand().isEmpty(); i++) {
                DomCard theCardToTrash = owner.getCardsInHand().get(0);
                if (theCardToTrash.getTrashPriority() >= 16
                        || (owner.getTotalMoneyInDeck() - theCardToTrash.getPotentialCoinValue() < theMin$Indeck && theCardToTrash.getPotentialCoinValue() > 0)) {
                    return;
                }
                owner.trash(owner.removeCardFromHand(theCardToTrash));
            }
        }
    }

    private void handleHumanPlayer() {
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("Choose up to 4 cards to trash" , owner.getCardsInHand(), theChosenCards, 0);
        while(theChosenCards.size()>4) {
            theChosenCards = new ArrayList<DomCard>();
            owner.getEngine().getGameFrame().askToSelectCards("Choose up to 4 cards to trash", owner.getCardsInHand(), theChosenCards, 0);
        }
        for (DomCard theCard: theChosenCards) {
            owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theCard.getName()).get(0)));
        }
    }
}