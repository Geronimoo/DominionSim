package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class StewardCard extends DomCard {
    public StewardCard () {
      super( DomCardName.Steward);
    }

    public void play() {
//    	if (owner.getActionsLeft()>0 && owner.getDeckSize()>0) {
//    	  owner.drawCards( 2 );
//    	  return;
//    	}
         if (!owner.getCardsFromHand(DomCardName.Curse).isEmpty() || hasTwoCrapCards()) {
             if (!playForTrash())
                 if (!playForMoney())
                     if (!playForCards())
                         owner.addAvailableCoins(2);
             return;
         }

        if (owner.getActionsLeft()>0 && owner.getDeckSize()>0) {
            playForCards();
            return;
        }
         //get the money if it ensures a better buy
         if (!playForMoney())
             //trash if there are 2 cards you want to trash
             if (!playForTrash())
           //draw 2 cards
           if (!playForCards())
        	 //if your deck is empty get $2 anyway (this might have positive effects with +buys
        	 //which are not accounted for (yet)
        	 owner.addAvailableCoins(2);
    }

        private boolean hasTwoCrapCards() {
            int counter = 0;
            for (DomCard theCard : owner.getCardsInHand()) {
                if (theCard.getTrashPriority()<DomCardName.Copper.getTrashPriority())
                    counter++;
            }
            return counter>1;
        }

    private boolean playForCards() {
      if (owner.getDeckSize()==0){
    	  return false;
      }
      owner.drawCards( 2 );
      return true;
    }

    private boolean playForMoney() {
      if (owner.addingThisIncreasesBuyingPower(new DomCost(2, 0))) {
    	owner.addAvailableCoins(2);
        return true;
      }
      return false;
    }

    private boolean playForTrash() {
        if (!owner.stillInEarlyGame()) {
            if (owner.getCardsFromHand(DomCardName.Curse).isEmpty() && (owner.getCardsFromHand(DomCardType.Ruins).isEmpty()))
                return false;
        }
        ArrayList<DomCard> cardsInHand = owner.getCardsInHand();
        if (cardsInHand.isEmpty())
        	return false;
        if (cardsInHand.size()==1){
        	if (cardsInHand.get(0).getTrashPriority()<16){
              owner.trash(owner.removeCardFromHand( cardsInHand.get(0)));
              return true;
        	} else {
              return false;
        	}
        }
        Collections.sort(cardsInHand,SORT_FOR_TRASHING);
        if (owner.getPlayStrategyFor(this)==DomPlayStrategy.modestTrashing) {
            if (cardsInHand.get(0).getTrashPriority()<DomCardName.Copper.getTrashPriority() && cardsInHand.get(1).getTrashPriority()<=DomCardName.Copper.getTrashPriority()) {
                owner.trash(owner.removeCardFromHand( cardsInHand.get(0)));
                owner.trash(owner.removeCardFromHand( cardsInHand.get(0)));
                return true;
            }
        }else {
            if (cardsInHand.get(0).getTrashPriority() < 20 && cardsInHand.get(1).getTrashPriority() < 20) {
                owner.trash(owner.removeCardFromHand(cardsInHand.get(0)));
                owner.trash(owner.removeCardFromHand(cardsInHand.get(0)));
                return true;
            }
        }
        return false;
    }
}