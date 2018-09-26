package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class PrinceCard extends DomCard {
    public PrinceCard() {
        super( DomCardName.Prince);
    }

    public void play() {
        if (owner==null)
            return;
        Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
        ArrayList<DomCard> princeableCards = (ArrayList<DomCard>) getPrinceableCards();
        if (princeableCards.isEmpty())
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            DomCard theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCardWithDomCard("Prince a card", princeableCards, "Mandatory!");
            if (owner.getCardsFromPlay(getName()).contains(this))
                owner.removeCardFromPlay(this);
            if (DomEngine.haveToLog)
                DomEngine.addToLog(owner + " sets aside " + theChosenCard + " with " + this);
            owner.setAsideForPrince(owner.removeCardFromHand(theChosenCard));
        } else {
            if (owner.getCardsFromPlay(getName()).contains(this))
                owner.removeCardFromPlay(this);
            DomCard theCard = princeableCards.get(0);
            if (DomEngine.haveToLog)
                DomEngine.addToLog(owner + " sets aside " + theCard + " with " + this);
            owner.setAsideForPrince(owner.removeCardFromHand(theCard));
        }
    }

    @Override
    public boolean wantsToBePlayed() {
        return !getPrinceableCards().isEmpty();
    }

    @Override
    public int getPlayPriority() {
        List<DomCard> theCards = new ArrayList<DomCard>();
        theCards.addAll(getPrinceableCards());
        Collections.sort(theCards, Comparator.comparingInt(DomCard::getPlayPriority));
        if (!theCards.isEmpty()) {
            return theCards.get(0).getPlayPriority() - 1;
        }
        return super.getPlayPriority();
    }

    private List<DomCard> getPrinceableCards() {
        return owner.getCardsInHand().stream()
                .filter(this::princeableCard)
                .collect(Collectors.toList());
    }

    private boolean princeableCard(DomCard theCard) {
        return theCard.hasCardType(DomCardType.Action) && !theCard.getName().equals(DomCardName.Prince) &&
                theCard.getCost(owner.getCurrentGame()).customCompare(new DomCost(4, 0)) <= 0;
    }
}