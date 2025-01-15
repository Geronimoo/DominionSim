package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class SailorCard extends DomCard {
    public SailorCard() {
      super( DomCardName.Sailor);
    }

    public void play() {
        owner.addActions(1);
        owner.triggerSailor();
    }

    @Override
    public void resolveDuration() {
        owner.addAvailableCoins(2);
        Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
        DomCard theCardToTrash = owner.getCardsInHand().get(0);
        if (theCardToTrash.getTrashPriority() < 16) {
            if (!owner.removingReducesBuyingPower(theCardToTrash)) {
                owner.trash(owner.removeCardFromHand(theCardToTrash));
            }
        }
    }
}