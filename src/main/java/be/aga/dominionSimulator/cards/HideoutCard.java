package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class HideoutCard extends DomCard {
    public HideoutCard() {
      super( DomCardName.Hideout);
    }

    public void play() {
      owner.drawCards(1);
      owner.addActions(2);
      if (!owner.getCardsInHand().isEmpty()) {
        DomCardName theChosenCard = null;
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsInHand()) {
                theChooseFrom.add(theCard.getName());
            }
            theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
            owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
        } else {
            theChosenCard=owner.getCardsInHand().get(0).getName();
            owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
        }
        if (theChosenCard.hasCardType(DomCardType.Victory))
            owner.gain(DomCardName.Curse);
      }
    }
}