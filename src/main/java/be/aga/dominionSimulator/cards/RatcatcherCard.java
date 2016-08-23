package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.security.spec.DSAParameterSpec;
import java.util.ArrayList;
import java.util.Collections;

public class RatcatcherCard extends DomCard {
    public RatcatcherCard() {
      super( DomCardName.Ratcatcher);
    }

    public void play() {
        owner.addActions(1);
        owner.drawCards(1);
        if (owner.getCardsInPlay().contains(this))
            owner.putOnTavernMat(owner.removeCardFromPlay(this));
    }

    @Override
    public void doWhenCalled() {
        Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
        DomCard theCardToTrash = owner.getCardsInHand().get(0);
        owner.trash(owner.removeCardFromHand(theCardToTrash));
    }

    public boolean wantsToBeCalled() {
        if (owner.getCardsInHand().isEmpty())
            return false;
        Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
        DomCard theCardToTrash = owner.getCardsInHand().get(0);
        if (theCardToTrash.getTrashPriority()<DomCardName.Copper.getTrashPriority())
            return true;
        if (theCardToTrash.getTrashPriority()==DomCardName.Copper.getTrashPriority()) {
            if (owner.getPlayStrategyFor(this)==DomPlayStrategy.aggressiveTrashing)
              return true;
            if (!owner.removingReducesBuyingPower(theCardToTrash))
                return true;
        }
        return false;
    }
}