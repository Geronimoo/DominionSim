package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class CatapultCard extends DomCard {
    public CatapultCard() {
      super( DomCardName.Catapult);
    }

    public void play() {
        owner.addAvailableCoins(1);
        if (owner.getCardsInHand().isEmpty())
            return;
        DomCard theCardToTrash=null;
        if (owner.isHumanOrPossessedByHuman()) {
            owner.setNeedsToUpdate();
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsInHand()) {
                theChooseFrom.add(theCard.getName());
            }
            theCardToTrash = owner.getCardsFromHand(owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!")).get(0);
        } else {
            Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
            theCardToTrash = owner.getCardsInHand().get(0);
        }
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

    @Override
    public int getTrashPriority() {
        if (owner.countInDeck(DomCardName.Rocks)<4 && owner.getCurrentGame().countInSupply(DomCardName.Rocks)==0)
            return DomCardName.Silver.getTrashPriority()-1;
        return super.getTrashPriority();
    }

    @Override
    public boolean wantsToBePlayed() {
        for (DomCard theCard:owner.getCardsInHand()){
            if (theCard==this)
                continue;
            if (theCard.getTrashPriority()<=DomCardName.Silver.getTrashPriority())
                return true;
        }
        return false;
    }
}