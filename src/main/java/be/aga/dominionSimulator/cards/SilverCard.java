package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.Collections;

public class SilverCard extends DomCard {

    public SilverCard() {
        super(DomCardName.Silver);
    }

    @Override
    public void play() {
        super.play();
        if (owner.getMerchantsPlayed()>0) {
            if (DomEngine.haveToLog) DomEngine.addToLog(owner + " played " + owner.getMerchantsPlayed() + " Merchants");
            owner.addAvailableCoins(owner.getMerchantsPlayed());
            owner.resetMerchantsPlayed();
        }
        for (DomCard theSauna : owner.getCardsFromPlay(DomCardName.Sauna)) {
            if (owner.getCardsInHand().isEmpty())
                return;
            Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
            if (owner.getCardsInHand().get(0).getTrashPriority() <= DomCardName.Copper.getTrashPriority()) {
                if (DomEngine.haveToLog) DomEngine.addToLog(DomCardName.Sauna.toHTML() + " cleans the hand");
                owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
            }
        }
    }

    @Override
    public int getTrashPriority() {
        if (owner==null)
            return super.getTrashPriority();
        if (owner.getPlayStrategyFor(this)== DomPlayStrategy.trashWhenObsolete) {
            if (owner.countInDeck(DomCardName.Gold)>0 && owner.countInDeck(DomCardName.King$s_Court)>0)
                return 15;
        }
        return super.getTrashPriority();
    }

    @Override
    public void doWhenGained() {
        if (owner.getCurrentGame().getBoard().isLandmarkActive(DomCardName.Aqueduct))
            owner.getCurrentGame().getBoard().moveVPFromTo(this.getName(),DomCardName.Aqueduct);
    }
}