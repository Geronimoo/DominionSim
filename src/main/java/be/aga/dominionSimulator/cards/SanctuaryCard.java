package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class SanctuaryCard extends DomCard {
    public SanctuaryCard() {
      super( DomCardName.Sanctuary);
    }

    public void play() {
      owner.addActions(1);
      owner.addAvailableBuys(1);
      owner.drawCards(1);
      if (owner.getCardsInHand().isEmpty())
          return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      if (!owner.getCardsFromHand(DomCardName.Menagerie).isEmpty() && !DomPlayer.getMultiplesInHand((MenagerieCard) owner.getCardsFromHand(DomCardName.Menagerie).get(0)).isEmpty()) {
          ArrayList<DomCard> theMultiples = DomPlayer.getMultiplesInHand((MenagerieCard) owner.getCardsFromHand(DomCardName.Menagerie).get(0));
          Collections.sort(theMultiples, SORT_FOR_DISCARDING);
          if (theMultiples.get(0).getDiscardPriority(1) <= DomCardName.Gold.getDiscardPriority(1)) {
              owner.exile(owner.removeCardFromHand(theMultiples.get(0)));
          }
      } else {
          Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARDING);
          if (owner.getCardsInHand().get(0).getDiscardPriority(1) <= DomCardName.Copper.getDiscardPriority(1)
                  &&(owner.stillInEarlyGame()||!owner.removingReducesBuyingPower(owner.getCardsInHand().get(0)))) {
              owner.exile(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
          }
      }
    }

    private void handleHuman() {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Exile a card?", theChooseFrom, "Don't Exile!!");
        if (theChosenCard!=null)
            owner.exile(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
    }
}