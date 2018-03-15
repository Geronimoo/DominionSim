package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class Haunted_MirrorCard extends DomCard {
    public Haunted_MirrorCard() {
      super( DomCardName.Haunted_Mirror);
    }

    private void handleHuman() {
        ArrayList<DomCard> theActions = owner.getCardsFromHand(DomCardType.Action);
        DomCard theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCardWithDomCard("Discard?", theActions, "Don't discard");
        if (theChosenCard!=null) {
            owner.discardFromHand(theChosenCard);
            owner.gain(DomCardName.Ghost);
        }
    }

    @Override
    public void doWhenTrashed() {
        ArrayList<DomCard> theActions = owner.getCardsFromHand(DomCardType.Action);
        if (!theActions.isEmpty()) {
            if (owner.isHumanOrPossessedByHuman()) {
                handleHuman();
            } else {
                Collections.sort(theActions, SORT_FOR_DISCARD_FROM_HAND);
                owner.discardFromHand(theActions.get(0));
                owner.gain(DomCardName.Ghost);
            }
        }
    }
}