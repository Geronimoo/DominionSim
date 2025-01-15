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
        ArrayList<DomCard> nonVictoryCards = new ArrayList<DomCard>();
        for (DomCard card : owner.getCardsInHand()) {
            if (!card.hasCardType(DomCardType.Victory))
                nonVictoryCards.add(card);
        }
        if (nonVictoryCards.isEmpty())
            nonVictoryCards.addAll(owner.getCardsInHand());
        Collections.sort( nonVictoryCards , SORT_FOR_TRASHING);
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsInHand()) {
                theChooseFrom.add(theCard.getName());
            }
            theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
            owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
        } else {
            theChosenCard=nonVictoryCards.get(0).getName();
            owner.trash(owner.removeCardFromHand(nonVictoryCards.get(0)));
        }
        if (theChosenCard.hasCardType(DomCardType.Victory))
            owner.gain(DomCardName.Curse);
      }
    }
}