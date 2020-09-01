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
        //for testing purposes
//        if (owner.getName().startsWith("Steward 2") && owner.getTurns()==1) {
//            Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
//            owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
//            owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
//            return;
//        }
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
//    	if (owner.getActionsAndVillagersLeft()>0 && owner.getDeckAndDiscardSize()>0) {
//    	  owner.drawCards( 2 );
//    	  return;
//    	}
         if (!owner.getCardsFromHand(DomCardName.Curse).isEmpty() || hasTwoCrapCards() || (owner.getPlayStrategyFor(this)==DomPlayStrategy.aggressiveTrashing)) {
             if (!playForTrash())
                 if (!playForMoney())
                     if (!playForCards())
                         owner.addAvailableCoins(2);
             return;
         }

        if (owner.getActionsAndVillagersLeft()>0 && owner.getDeckAndDiscardSize()>0) {
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

    private void handleHuman() {
        ArrayList<String> theOptions = new ArrayList<String>();
        theOptions.add("+$2");
        theOptions.add("+2 Cards");
        theOptions.add("Trash 2 cards");
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Select for Steward", theOptions, "Mandatory!");
        if (theChoice == 0)
            owner.addAvailableCoins(2);
        if (theChoice == 1)
            owner.drawCards(2);
        if (theChoice == 2) {
            if (owner.getCardsInHand().size()>2) {
                ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
                owner.getEngine().getGameFrame().askToSelectCards("Choose 2 cards to trash", owner.getCardsInHand(), theChosenCards, 2);
                for (DomCard theCard:theChosenCards) {
                    owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theCard.getName()).get(0)));
                }
            } else {
                while (!owner.getCardsInHand().isEmpty())
                    owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
            }
        }
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
      if (owner.getDeckAndDiscardSize()==0){
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
        	if (cardsInHand.get(0).getTrashPriority()<16 && owner.getTotalMoneyInDeck()>5){
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
            if (cardsInHand.get(0).getTrashPriority() < 20 && cardsInHand.get(1).getTrashPriority() < 20 && owner.getTotalMoneyInDeck()>5) {
                owner.trash(owner.removeCardFromHand(cardsInHand.get(0)));
                owner.trash(owner.removeCardFromHand(cardsInHand.get(0)));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}