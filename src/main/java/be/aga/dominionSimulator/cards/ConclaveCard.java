package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class ConclaveCard extends DomCard {

    public ConclaveCard() {
        super(DomCardName.Conclave);
    }

    public void play() {
        owner.addAvailableCoins(2);
        int theCount = 0;
        for (DomCard theCard : owner.getCardsInHand()) {
            if (owner.getCardsFromPlay(theCard.getName()).isEmpty())
                theCount++;
        }
        if (theCount == 0)
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
        } else {
            if (getNextActionToPlay() != null) {
               owner.play(owner.removeCardFromHand(getNextActionToPlay()));
               owner.addActions(1);
            }
        }
    }

    private void handleHuman() {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        ArrayList<DomCard> theActionsToConsider = owner.getCardsFromHand(DomCardType.Action);
        if (theActionsToConsider.isEmpty())
            return;
        Collections.sort(theActionsToConsider, DomCard.SORT_FOR_PLAYING);
        for (DomCard card : theActionsToConsider) {
            if (owner.getCardsFromPlay(card.getName()).isEmpty()) {
                theChooseFrom.add(card.getName());
            }
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Play a card?", theChooseFrom, "Don't play a card");
        if (theChosenCard != null) {
            owner.play(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
            owner.addActions(1);
        }
    }

    public DomCard getNextActionToPlay() {
        ArrayList<DomCard> theActionsToConsider = owner.getCardsFromHand(DomCardType.Action);
        if (theActionsToConsider.isEmpty())
            return null;
        Collections.sort(theActionsToConsider, DomCard.SORT_FOR_PLAYING);
        for (DomCard card : theActionsToConsider) {
            if (card.wantsToBePlayed() && owner.getCardsFromPlay(card.getName()).isEmpty() )
                return card;
        }
        return null;
    }

    @Override
    public int getPlayPriority() {
        ArrayList<DomCard> theActionsToConsider = owner.getCardsFromHand(DomCardType.Action);
        if (theActionsToConsider.size()==1)
            return super.getPlayPriority();
        for (DomCard card : theActionsToConsider) {
            if (card==this)
                continue;
            if (card.getName()!=DomCardName.Conclave && card.wantsToBePlayed() && owner.getCardsFromPlay(card.getName()).isEmpty() )
                return 2;
        }
        return super.getPlayPriority();
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}