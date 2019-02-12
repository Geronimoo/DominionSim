package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class SewersCard extends DomCard {
    public SewersCard() {
      super( DomCardName.Sewers);
    }

    @Override
    public void trigger() {
        if (owner.getCardsInHand().isEmpty())
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsInHand()) {
                theChooseFrom.add(theCard.getName());
            }
            DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Don't trash");
            if (theChosenCard!=null) {
                owner.setResolvingSewers(true);
                owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
                owner.setResolvingSewers(false);
            } else {
                if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " trashes nothing with "+ getName().toHTML());
            }
        } else {
            Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
            if (owner.getCardsInHand().get(0).getTrashPriority()<= DomCardName.Copper.getTrashPriority() && !owner.removingReducesBuyingPower(owner.getCardsInHand().get(0))) {
                owner.setResolvingSewers(true);
                owner.trash(owner.getCardsInHand().remove(0));
                owner.setResolvingSewers(false);
            }
        }
    }
}