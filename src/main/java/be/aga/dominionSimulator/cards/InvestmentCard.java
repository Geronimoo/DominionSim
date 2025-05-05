package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class InvestmentCard extends DomCard {
    public InvestmentCard() {
      super( DomCardName.Investment);
    }

    public void play() {
      if (owner.getCardsInHand().isEmpty()) {
          return;
      }
      Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
      if (owner.getCardsInHand().get(0).getTrashPriority()<=DomCardName.Copper.getTrashPriority()) {
          owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
          owner.addAvailableCoins(1);
      } else {
          owner.addVP(owner.getDifferentTreasuresInHand());
          owner.trash(owner.removeCardFromPlay(this));
      }
    }

    @Override
    public boolean wantsToBePlayed() {
        for (DomCard card : owner.getCardsInHand())
            if (card.getTrashPriority() <= DomCardName.Copper.getTrashPriority())
              return true;
        if (!owner.getDeck().deckHasJunkLeft())
            return true;
        return false;
    }
}