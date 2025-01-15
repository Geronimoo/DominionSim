package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class SilverCard extends DomCard {

    public SilverCard() {
        super(DomCardName.Silver);
    }

    @Override
    public void play() {
        if (owner.isEnviousActive()) {
            owner.addAvailableCoinsSilent(1);
        } else {
            owner.addAvailableCoinsSilent(2);
        }
        if (owner.getMerchantsPlayed()>0) {
            if (DomEngine.haveToLog) DomEngine.addToLog(owner + " played " + owner.getMerchantsPlayed() + " Merchants");
            owner.addAvailableCoins(owner.getMerchantsPlayed());
            owner.resetMerchantsPlayed();
        }
        final int numberOfSaunasInPlay = owner.getCardsFromPlay(DomCardName.Sauna).size();
        for (int i=0; i<numberOfSaunasInPlay; i++) {
            if (owner.getCardsInHand().isEmpty())
                return;
            if (owner.isHumanOrPossessedByHuman()) {
                owner.setNeedsToUpdateGUI();
                ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
                for (DomCard theCard : owner.getCardsInHand()) {
                    theChooseFrom.add(theCard.getName());
                }
                DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Don't trash!");
                if (theChosenCard==null)
                    return;
                owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
            } else {
                Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
                if (owner.getCardsInHand().get(0).getTrashPriority() < DomCardName.Copper.getTrashPriority()) {
                    if (DomEngine.haveToLog) DomEngine.addToLog(DomCardName.Sauna.toHTML() + " cleans the hand");
                    owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
                } else {
                    if (owner.getCardsInHand().get(0).getName() == DomCardName.Copper && owner.getBuysLeft() == 1) {
                        if (owner.getDesiredCard(owner.getTotalPotentialCurrency().add(new DomCost(-1, 0)), false) == owner.getDesiredCard(owner.getTotalPotentialCurrency(), false))
                            owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
                    }
                }
            }
        }
        if (owner.isAttackedByCorsair() && !owner.hasTrashedForCorsair() ) {
            owner.setTrashedForCorsair(true);
            owner.removeCardFromPlay(this);
            owner.trash(this);
        }
    }

    @Override
    public int getPlayPriority() {
        if (owner.count(DomCardName.Magic_Lamp)>0
                && !owner.getCardsFromHand(DomCardName.Magic_Lamp).isEmpty()
                && owner.getCardsFromPlay(DomCardName.Silver).isEmpty())
            return 5;
        return super.getPlayPriority();
    }

    @Override
    public int getTrashPriority() {
        if (owner==null)
            return super.getTrashPriority();
        if (owner.getPlayStrategyFor(this)== DomPlayStrategy.trashWhenObsolete) {
            if (owner.count(DomCardName.Gold)>0 && owner.count(DomCardName.King$s_Court)>0)
                return 15;
            if (owner.count(DomCardType.Action)>9 || owner.count(DomCardName.Silver)>3)
                return 15;
        }
        return super.getTrashPriority();
    }

    @Override
    public void doWhenGained() {
        if (owner.getCurrentGame().getBoard().isLandmarkActive(DomCardName.Aqueduct))
            owner.getCurrentGame().getBoard().moveVPFromTo(this.getName(),DomCardName.Aqueduct);
        super.doWhenGained();
    }
}