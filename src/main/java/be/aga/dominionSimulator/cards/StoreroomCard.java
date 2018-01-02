package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class StoreroomCard extends DomCard {
    public StoreroomCard() {
      super( DomCardName.Storeroom);
    }

    public void play() {
      owner.addAvailableBuys(1);
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      if (owner.getPlayStrategyFor(this)==DomPlayStrategy.cityQuarterCombo && !owner.getCardsFromHand(DomCardName.City_Quarter).isEmpty() && owner.getDeckSize()==0){
          Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
          int i=0;
          while (owner.getDeckSize()+2<owner.getCardsFromHand(DomCardType.Action).size()){
              owner.discard(owner.getCardsInHand().remove(0));
              i++;
          }
          owner.addAvailableCoins(i);
      } else {
          if (owner.getPlayStrategyFor(this)==DomPlayStrategy.crossroadsCombo){
              int theSize = owner.getCardsInHand().size();
              owner.discardHand();
              owner.addAvailableCoins(theSize);
          } else {
              playNormal();
          }
      }
    }

    private void handleHuman() {
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("Discard cards" , owner.getCardsInHand(), theChosenCards, 0);
        for (DomCard theCard : theChosenCards) {
            owner.discardFromHand(theCard.getName());
        }
        owner.drawCards(theChosenCards.size());
        owner.setNeedsToUpdate();
        theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("Discard for +$1" , owner.getCardsInHand(), theChosenCards, 0);
        for (DomCard theCard : theChosenCards) {
            owner.discardFromHand(theCard.getName());
        }
        owner.addAvailableCoins(theChosenCards.size());
    }

    private void playNormal() {
        Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
        int i=0;
        for (i=0;i<owner.getCardsInHand().size();i++) {
            if (owner.getCardsInHand().get(i).getDiscardPriority(owner.getActionsLeft())> DomCardName.Copper.getDiscardPriority(1))
                break;
        }
        for (int j=0;j<i;j++)
            owner.discardFromHand(owner.getCardsInHand().get(0));
        if (i>0)
          owner.drawCards(i);

        Collections.sort(owner.getCardsInHand(),SORT_FOR_DISCARD_FROM_HAND);
        for (i=0;i<owner.getCardsInHand().size();i++) {
            if (owner.getCardsInHand().get(i).getName()!=DomCardName.Storeroom && owner.getCardsInHand().get(i).getDiscardPriority(owner.getActionsLeft())>DomCardName.Copper.getDiscardPriority(1))
                break;
        }
        for (int j=0;j<i;j++)
            owner.discardFromHand(owner.getCardsInHand().get(0));
        if (i>0)
          owner.addAvailableCoins(i);
    }

    @Override
    public int getPlayPriority() {
        if (owner.getPlayStrategyFor(this)==DomPlayStrategy.cityQuarterCombo && !owner.getCardsFromHand(DomCardName.City_Quarter).isEmpty() && owner.getDeckSize()==0) {
            return 1;
        }
        return super.getPlayPriority();
    }
}