package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class Zombie_MasonCard extends DomCard {
    public Zombie_MasonCard() {
        super(DomCardName.Zombie_Mason);
    }

    public void play() {
        ArrayList<DomCard> theTopCards = owner.revealTopCards(1);
        if (theTopCards.isEmpty())
            return;
        DomCard theTopCard = theTopCards.get(0);
        owner.trash(theTopCard);
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman(theTopCard);
            return;
        }
        DomCardName theDesiredCard = owner.getDesiredCard(theTopCard.getCost(owner.getCurrentGame()).add(new DomCost(1, 0)), false);
        if (theDesiredCard != null) {
            owner.gain(theDesiredCard);
        }
    }

    private void handleHuman(DomCard theTopCard) {
        owner.setNeedsToUpdate();
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
            if (theTopCard.getCost(owner.getCurrentGame()).add(new DomCost(1, 0)).compareTo(theCard.getCost(owner.getCurrentGame())) >= 0 && owner.getCurrentGame().countInSupply(theCard) > 0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Gain a card for " + this.getName().toString(), theChooseFrom, "Don't gain!");
        if (theChosenCard != null)
            owner.gain(owner.getCurrentGame().takeFromSupply(theChosenCard));
    }
}
