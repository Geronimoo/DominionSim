package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class ShepherdCard extends DomCard {
    public ShepherdCard() {
      super( DomCardName.Shepherd);
    }

    public void play() {
        owner.addActions(1);
        if (owner.isHumanOrPossessedByHuman()){
            handleHuman();
            return;
        }
        Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
        int theCount = 0;
        while (!owner.getCardsFromHand(DomCardType.Victory).isEmpty()
                && owner.getCardsFromHand(DomCardType.Victory).get(0).getDiscardPriority(owner.actionsLeft)<DomCardName.Nobles.getDiscardPriority(1)) {
            owner.discardFromHand(owner.getCardsFromHand(DomCardType.Victory).get(0));
            theCount++;
        }
        if (theCount>0)
    	  owner.drawCards(theCount*2);
    }

    private void handleHuman() {
        owner.setNeedsToUpdate();
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("Discard?" , owner.getCardsFromHand(DomCardType.Victory), theChosenCards, 0);
        if (theChosenCards.isEmpty())
            return;
        for (DomCard theCard : theChosenCards) {
            owner.discardFromHand(theCard.getName());
        }
        owner.drawCards(theChosenCards.size()*2);
    }

    @Override
    public int getPlayPriority() {
        if (owner.getCardsFromHand(DomCardType.Victory).isEmpty())
            return 60;
        return super.getPlayPriority();
    }
}