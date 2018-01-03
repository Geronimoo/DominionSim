package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import java.util.ArrayList;
import java.util.Collections;

public class SaveCard extends DomCard {
    public SaveCard() {
        super(DomCardName.Save);
    }

    public void play() {
        if (owner.isSaveActivated())
            return;
        owner.setSaveActivated();
        owner.addAvailableBuys(1);
        if (owner.getCardsInHand().isEmpty())
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsInHand()) {
                theChooseFrom.add(theCard.getName());
            }
            DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Save a card", theChooseFrom, "Mandatory!");
            if (DomEngine.haveToLog) DomEngine.addToLog(owner + " sets aside " + theChosenCard);
            owner.setAsideToSave(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
        } else {
            Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARDING);
            DomCard theCardToSave = owner.getCardsInHand().get(owner.getCardsInHand().size() - 1);
            if (DomEngine.haveToLog) DomEngine.addToLog(owner + " sets aside " + theCardToSave);
            owner.setAsideToSave(owner.removeCardFromHand(theCardToSave));
        }
    }
}