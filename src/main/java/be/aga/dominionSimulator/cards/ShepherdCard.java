package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

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
        int theCount = 0;
        for (DomCard theCard:owner.getCardsFromPlay(DomCardType.Victory)) {
            if (theCard.getDiscardPriority(owner.getActionsLeft())<DomCardName.Nobles.getDiscardPriority(1)) {
                owner.discardFromHand(theCard);
                theCount++;
            }
        }
        if (theCount>0)
    	  owner.drawCards(theCount*2);
    }

    private void handleHuman() {
        owner.setNeedsToUpdate();
        ArrayList<DomCardName> theChosenCards = new ArrayList<DomCardName>();
        owner.getEngine().getGameFrame().askToSelectCards("Discard?" , owner.getCardsFromHand(DomCardType.Victory), theChosenCards, 0);
        if (theChosenCards.isEmpty())
            return;
        for (DomCardName theCard : theChosenCards) {
            owner.discardFromHand(theCard);
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