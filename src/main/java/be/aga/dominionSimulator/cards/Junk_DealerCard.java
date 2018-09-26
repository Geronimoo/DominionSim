package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import java.util.ArrayList;
import java.util.Collections;

public class Junk_DealerCard extends DomCard {
    public Junk_DealerCard() {
      super( DomCardName.Junk_Dealer);
    }

    public void play() {
      owner.addActions(1);
      owner.addAvailableCoins(1);
      owner.drawCards(1);
      if (owner.getCardsInHand().isEmpty())
          return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      if (!owner.getCardsFromHand(DomCardName.Menagerie).isEmpty() && !DomPlayer.getMultiplesInHand((MenagerieCard) owner.getCardsFromHand(DomCardName.Menagerie).get(0)).isEmpty()) {
          ArrayList<DomCard> theMultiples = DomPlayer.getMultiplesInHand((MenagerieCard) owner.getCardsFromHand(DomCardName.Menagerie).get(0));
          Collections.sort(theMultiples, SORT_FOR_TRASHING);
          if (theMultiples.get(0).getTrashPriority()<=DomCardName.Gold.getTrashPriority(owner))
              owner.trash(owner.removeCardFromHand( theMultiples.get(0) ));
          else {
              Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
              owner.trash(owner.removeCardFromHand( owner.getCardsInHand().get( 0 ) ));
          }
      } else {
          Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
          owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
      }
    }

    private void handleHuman() {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
        owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
    }
}