package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class FearCard extends DomCard {
    public FearCard() {
      super( DomCardName.Fear);
    }

    public void play() {
        if (owner.getCardsInHand().size()<5)
            return;
        if (owner.getCardsFromHand(DomCardType.Action).isEmpty() && owner.getCardsFromHand(DomCardType.Treasure).isEmpty()) {
            if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has no Actions or Treasures in hand");
        } else {
            if (owner.isHumanOrPossessedByHuman()) {
                handleHuman();
            } else {
                Collections.sort(owner.getCardsInHand(),SORT_FOR_DISCARD_FROM_HAND);
                int i=0;
                while (!owner.getCardsInHand().get(i).hasCardType(DomCardType.Action) && !owner.getCardsInHand().get(i).hasCardType(DomCardType.Treasure))
                    i++;
                owner.discard(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
            }
        }
    }

    private void handleHuman() {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        ArrayList<DomCard> theCards = new ArrayList<DomCard>();
        theCards.addAll(owner.getCardsFromHand(DomCardType.Action));
        theCards.addAll(owner.getCardsFromHand(DomCardType.Treasure));
        owner.getEngine().getGameFrame().askToSelectCards("Discard" , theCards, theChosenCards, 1);
        owner.discard(owner.removeCardFromHand(theChosenCards.get(0)));
    }
}