package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class MillCard extends DomCard {
    public MillCard() {
      super( DomCardName.Mill);
    }

    public void play() {
        owner.addActions(1);
        owner.drawCards(1);
        if (owner.getCardsInHand().size() < 2)
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
        } else {
            if (!owner.getCardsFromHand(DomCardName.Menagerie).isEmpty()) {
                processMenagerie();
            } else {
                Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
                if (owner.getCardsInHand().get(1).getDiscardPriority(owner.getActionsAndVillagersLeft()) <= DomCardName.Copper.getDiscardPriority(1)) {
                    owner.discard(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
                    owner.discard(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
                    owner.addAvailableCoins(2);
                }
            }
        }
    }

    private void processMenagerie() {
        MenagerieCard theMenagerie = (MenagerieCard) owner.getCardsFromHand(DomCardName.Menagerie).get(0);
        ArrayList<DomCard> theCardsToDiscard = DomPlayer.getMultiplesInHand(theMenagerie);
        if (theCardsToDiscard.size() < 2)
            return;
        Collections.sort(theCardsToDiscard, SORT_FOR_DISCARDING);
        for (int i = 0; i < 2; i++) {
            owner.discardFromHand(theCardsToDiscard.remove(0));
        }
        owner.addAvailableCoins(2);
    }

    private void handleHuman() {
        owner.setNeedsToUpdateGUI();
        if (owner.getEngine().getGameFrame().askPlayer("<html>Use " + DomCardName.Mill.toHTML() +"</html>", "Resolving Mill" )) {
            owner.doForcedDiscard(2, false);
            owner.addAvailableCoins(2);
        }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}