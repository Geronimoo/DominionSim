package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import com.sun.java.browser.plugin2.DOM;

import java.util.ArrayList;

public class PlazaCard extends DomCard {
    public PlazaCard() {
      super( DomCardName.Plaza);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(1);
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      if (!owner.getCardsFromHand(DomCardName.Copper).isEmpty()) {
          owner.discardFromHand(DomCardName.Copper);
          owner.addCoinTokens(1);
      } else {
          if (!owner.getCardsFromHand(DomCardName.Masterpiece).isEmpty()) {
              owner.discardFromHand(DomCardName.Masterpiece);
              owner.addCoinTokens(1);
          }
      }
    }

    private void handleHuman() {
        owner.setNeedsToUpdate();
        if (owner.getCardsFromHand(DomCardType.Treasure).isEmpty())
            return;
        if (!owner.getEngine().getGameFrame().askPlayer("<html>Discard a treasure?", "Resolving " + this.getName().toString()))
            return;
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            if (theCard.hasCardType(DomCardType.Treasure))
              theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Discard a treasure", theChooseFrom, "Mandatory!");
        for (DomCard theCard : owner.getCardsInHand()) {
            if (theChosenCard==theCard.getName()) {
                owner.discardFromHand(theCard);
                owner.addCoinTokens(1);
                return;
            }
        }
    }
}