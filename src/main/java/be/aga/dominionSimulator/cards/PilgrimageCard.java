package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PilgrimageCard extends DomCard {
    public PilgrimageCard() {
      super( DomCardName.Pilgrimage);
    }

    public void play() {
        if (owner.isPilgrimageActivatedThisTurn())
            return;
        owner.activatePilgrimage();
        owner.flipJourneyToken();
        if (!owner.isJourneyTokenFaceUp())
          return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        Collections.sort(owner.getCardsInPlay(),SORT_FOR_TRASHING);
        Set theGainedCards = new HashSet<DomCardName>();
        int i=owner.getCardsInPlay().size()-1;
        while (theGainedCards.size()<3 && i>=0) {
            DomCardName theCardToConsider = owner.getCardsInPlay().get(i).getName();
            if (!theGainedCards.contains(theCardToConsider) && owner.getCurrentGame().countInSupply(theCardToConsider)>0 && theCardToConsider.getTrashPriority(owner)>DomCardName.Copper.getTrashPriority() && (theCardToConsider.hasCardType(DomCardType.Kingdom)||theCardToConsider.hasCardType(DomCardType.Base))) {
                owner.gain(theCardToConsider);
                theGainedCards.add(theCardToConsider);
            }
            i--;
        }
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChosenCards = new ArrayList<DomCardName>();
        do {
          theChosenCards = new ArrayList<DomCardName>();
          owner.getEngine().getGameFrame().askToSelectCards("Gain up to 3", owner.getUniqueCardsInPlay(), theChosenCards, 0);
        } while (theChosenCards.size()>3);
        for (DomCardName theCard:theChosenCards) {
          owner.gain(theCard);
        }
    }
}