package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class MercenaryCard extends DomCard {
    public MercenaryCard() {
      super( DomCardName.Mercenary);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      if (!hasTwoCrapCards()) {
          if (owner.getCardsInHand().size()==1 && owner.getCardsInHand().get(0).getTrashPriority()<=DomCardName.Copper.getTrashPriority()) {
              owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
              return;
          }
          if (DomEngine.haveToLog) DomEngine.addToLog( owner + " trashes nothing" );
          return;
      }
      Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
      owner.trash(owner.removeCardFromHand( owner.getCardsInHand().get(0)));
      owner.trash(owner.removeCardFromHand( owner.getCardsInHand().get(0)));
      owner.addAvailableCoins(2);
      owner.drawCards(2);
      for (DomPlayer thePlayer : owner.getOpponents()) {
          if (!thePlayer.checkDefense()) {
              thePlayer.doForcedDiscard(thePlayer.getCardsInHand().size()-3, false);
          }
      }

    }

    private void handleHuman() {
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        do {
            theChosenCards = new ArrayList<DomCard>();
            owner.getEngine().getGameFrame().askToSelectCards("Trash 2 ?", owner.getCardsInHand(), theChosenCards, 0);
        } while (theChosenCards.size()>2);
        for (DomCard theCard : theChosenCards) {
            owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theCard.getName()).get(0)));
        }
        if (theChosenCards.size()==2) {
            owner.addAvailableCoins(2);
            owner.drawCards(2);
            for (DomPlayer thePlayer : owner.getOpponents()) {
                if (!thePlayer.checkDefense()) {
                  thePlayer.doForcedDiscard(thePlayer.getCardsInHand().size()-3, false);
                }
            }
        }
    }

    private boolean hasTwoCrapCards() {
        int counter = 0;
        for (DomCard theCard : owner.getCardsInHand()) {
            if (theCard.getTrashPriority()<=DomCardName.Copper.getTrashPriority())
                counter++;
        }
        return counter>1;
    }

    @Override
    public boolean wantsToBePlayed() {
        return hasTwoCrapCards();
    }
}