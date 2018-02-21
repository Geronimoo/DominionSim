package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class MonasteryCard extends DomCard {
    public MonasteryCard() {
      super( DomCardName.Monastery);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHumanPlayer();
        } else {
            int theTrashCount = 0;
            for (DomCard theCard : owner.getCardsInHand()) {
                if (theCard.getTrashPriority() < 16) {
                    theTrashCount++;
                }
            }
            Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
            for (int i = 0; i < owner.getCardsGainedLastTurn().size(); i++) {
                DomCard theCardToTrash = null;
                if (!owner.getCardsInHand().isEmpty()) {
                    theCardToTrash = owner.getCardsInHand().get(0);
                }
                if (theCardToTrash==null || theCardToTrash.getTrashPriority() >= 16) {
                    if (owner.getCardsFromPlay(DomCardName.Copper).isEmpty()) {
                        return;
                    } else {
                        owner.trash(owner.removeCardFromPlay(owner.getCardsFromPlay(DomCardName.Copper).get(0)));
                    }
                } else {
                    owner.trash(owner.removeCardFromHand(theCardToTrash));
                }
            }
        }
    }

    private void handleHumanPlayer() {
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        ArrayList<DomCard> theChooseFrom = new ArrayList<DomCard>();
        theChooseFrom.addAll(owner.getCardsInHand());
        theChooseFrom.addAll(owner.getCardsFromPlay(DomCardName.Copper));
        if (theChooseFrom.isEmpty())
            return;
        owner.getEngine().getGameFrame().askToSelectCards("Choose up to " + owner.getCardsGainedLastTurn().size()+" cards to trash" , theChooseFrom, theChosenCards, 0);
        while(theChosenCards.size()>owner.getCardsGainedLastTurn().size()) {
            theChosenCards = new ArrayList<DomCard>();
            owner.getEngine().getGameFrame().askToSelectCards("Choose up to " + owner.getCardsGainedLastTurn().size()+" cards to trash" , theChooseFrom, theChosenCards, 0);
        }
        for (DomCard theCard: theChosenCards) {
            if (owner.getCardsInHand().contains(theCard)) {
                owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theCard.getName()).get(0)));
            } else {
                owner.trash(owner.removeCardFromPlay(theCard));
            }
        }
    }
}