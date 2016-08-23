package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.Collections;
import java.util.HashSet;

public class CatapultCard extends DomCard {
    public CatapultCard() {
      super( DomCardName.Catapult);
    }

    public void play() {
        owner.addAvailableCoins(1);
        if (owner.getCardsInHand().size()==0)
            return;
        Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
        DomCard theCardToTrash = owner.getCardsInHand().get(0);
        if (!owner.getCardsFromHand(DomCardName.Rocks).isEmpty())
            theCardToTrash=owner.getCardsFromHand(DomCardName.Rocks).get(0);
        owner.trash(owner.removeCardFromHand(theCardToTrash));
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (!thePlayer.checkDefense()) {
                if (theCardToTrash.getCoinCost(owner.getCurrentGame())>=3)
                  thePlayer.gain(DomCardName.Curse);
                if (theCardToTrash.hasCardType(DomCardType.Treasure))
                  thePlayer.doForcedDiscard(thePlayer.getCardsInHand().size()-3, false);
            }
        }
    }
}