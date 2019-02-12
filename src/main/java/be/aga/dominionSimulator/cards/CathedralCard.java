package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class CathedralCard extends DomCard {
    public CathedralCard() {
      super( DomCardName.Cathedral);
    }

    public void trigger() {
      if (!owner.getCardsInHand().isEmpty()) {
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsInHand()) {
                theChooseFrom.add(theCard.getName());
            }
            DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
            if (theChosenCard != null) {
                owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
            }
        } else {
            if (owner.removingReducesBuyingPower(owner.getCardsInHand().get(0)) && !owner.getOpponents().isEmpty() && owner.countVictoryPoints()>owner.getOpponents().get(0).countVictoryPoints()&& !owner.getCardsFromHand(DomCardType.Victory).isEmpty()) {
                owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(DomCardType.Victory).get(0)));
            } else {
                if (owner.getTotalMoneyInDeck()<8 && !owner.getCardsFromHand(DomCardType.Victory).isEmpty()) {
                    owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(DomCardType.Victory).get(0)));
                } else {
                    owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
                }
            }
        }
      }
    }
}