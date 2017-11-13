package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Trade_RouteCard extends DomCard {
    public Trade_RouteCard () {
      super( DomCardName.Trade_Route);
    }

    public void play() {
      owner.addAvailableBuys(1);
      owner.addAvailableCoins(owner.getCurrentGame().getBoard().countTradeRouteTokens());
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
            owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
        }
      }
    }
}