package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class CourierCard extends DomCard {

    public CourierCard() {
        super(DomCardName.Courier);
    }

    public void play() {
        owner.addAvailableCoins(1);
        ArrayList<DomCard> topCard = owner.revealTopCards(1);
        if (topCard.isEmpty() && owner.getCardsFromDiscard().isEmpty())
            return;
        owner.discard(topCard);
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
        } else {
            ArrayList<DomCard> tempCards = new ArrayList<>();
            tempCards.addAll(owner.getCardsFromDiscard());
            tempCards.sort(SORT_FOR_DISCARDING_REVERSE);
            for (DomCard card : tempCards) {
                if (card.hasCardType(DomCardType.Treasure)||card.hasCardType(DomCardType.Action)) {
                    if (card.wantsToBePlayed()) {
                        owner.play(owner.removeCardFromDiscard(card));
                        break;
                    }
                }
            }
        }
    }

    private void handleHuman() {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCard> theChooseFrom = new ArrayList<>();
        for(DomCard card : owner.getCardsFromDiscard()) {
            if (card.hasCardType(DomCardType.Action)||card.hasCardType(DomCardType.Treasure))
                theChooseFrom.add(card);
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCard theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCardWithDomCard("Play a card from Discard?", theChooseFrom, "Don't play a card");
        if (theChosenCard != null) {
            owner.play(owner.removeCardFromDiscard(theChosenCard));
        }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        for(DomCard card : owner.getCardsFromDiscard()) {
            if (card.getName()==DomCardName.Courier || card.hasCardType(DomCardType.Action))
                if (aType==DomCardType.Terminal)
                    return false;
        }

        return super.hasCardType(aType);

    }
}