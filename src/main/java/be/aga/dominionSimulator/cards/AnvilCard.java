package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class AnvilCard extends DomCard {
    public AnvilCard() {
      super( DomCardName.Anvil);
    }

    public void play() {
      owner.addAvailableCoins(1);
        DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(4, 0), false);
        if (theDesiredCard != null) {
            ArrayList<DomCard> treasuresInHand = owner.getCardsFromHand(DomCardType.Treasure);
            if (treasuresInHand.isEmpty())
                return;
            Collections.sort(treasuresInHand, SORT_FOR_DISCARDING);
            if (!owner.removingReducesBuyingPower(treasuresInHand.get(0))
            || (owner.getDesiredCard(owner.getTotalPotentialCurrency(),false)!=null
                    && owner.getDesiredCard(owner.getTotalPotentialCurrency(),false).getCoinCost(owner)<=4)){
                owner.discardFromHand(treasuresInHand.get(0));
                WorkshopCard.doWorkshop(owner);
            }
        }
    }
}