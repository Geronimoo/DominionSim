package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class BanishCard extends DomCard {
    public BanishCard() {
      super( DomCardName.Banish);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARDING);
      DomCard bestCard = null;
      for (DomCard card : owner.getCardsInHand()) {
          if (card.getDiscardPriority(1)>DomCardName.Copper.getDiscardPriority(1))
              break;
          if (bestCard==null ) {
              bestCard = card;
          } else {
              if (owner.getCardsFromHand(card.getName()).size()>owner.getCardsFromHand(bestCard.getName()).size()) {
                  bestCard = card;
              }
          }
      }
      if (bestCard!=null) {
          for (DomCard card:owner.getCardsFromHand(bestCard.getName())) {
              owner.moveToExileMat(owner.removeCardFromHand(card));
          }
      }
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Exile cards", theChooseFrom, "Mandatory!");
        for (DomCard card:owner.getCardsFromHand(theChosenCard)) {
            owner.moveToExileMat(owner.removeCardFromHand(card));
        }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }
}