package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class The_Earth$s_GiftCard extends DomCard {
    public The_Earth$s_GiftCard() {
      super( DomCardName.The_Earth$s_Gift);
    }

    public void play() {
      if (owner.getCardsFromHand(DomCardType.Treasure).isEmpty())
         return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHumanPlayer();
      }else {
          DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(4, 0), false);
          if (theDesiredCard == null)
              return;
          Collections.sort(owner.getCardsInHand(),SORT_FOR_DISCARDING);
          DomCard theTreasure = owner.getCardsFromHand(DomCardType.Treasure).get(0);
          if (!owner.removingReducesBuyingPower(theTreasure)) {
              owner.discardFromHand(theTreasure);
              owner.gain(theDesiredCard);
          }
      }
    }

    private void handleHumanPlayer() {
        if (!owner.getEngine().getGameFrame().askPlayer("<html>Discard a Treasure?" + DomCardName.Copper.toHTML() +"</html>", "Resolving " + this.getName().toString()))
           return;
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            if (theCard.hasCardType(DomCardType.Treasure))
                theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Discard a Treasure", theChooseFrom, "Mandatory!");
        owner.discardFromHand(theChosenCard);
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (new DomCost(4,0).customCompare(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0 )
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Gain a card", theChooseFrom, "Mandatory!")));
    }
}