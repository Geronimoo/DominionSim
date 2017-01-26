package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.Collections;

public class SaveCard extends DomCard {
    public SaveCard() {
      super( DomCardName.Save);
    }

    public void play() {
        owner.setSaveActivated();
        owner.addAvailableBuys(1);
        if (owner.getCardsInHand().isEmpty())
            return;
        Collections.sort(owner.getCardsInHand(),SORT_FOR_DISCARDING);
        DomCard theCardToSave = owner.getCardsInHand().get(owner.getCardsInHand().size() - 1);
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " sets aside " + theCardToSave);
        owner.setAsideToSave(owner.removeCardFromHand(theCardToSave));
   }
}