package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class AmuletCard extends DomCard {
    public AmuletCard() {
      super( DomCardName.Amulet);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        if (owner.getPlayStrategyFor(this) == DomPlayStrategy.aggressiveTrashing) {
            if (!owner.getCardsFromHand(DomCardName.Squire).isEmpty() && owner.count(DomCardType.Attack)==0) {
                owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Squire).get(0)));
                return;
            }

            if (!playForAgroTrash())
                if (!playForMoney())
                    owner.gain(DomCardName.Silver);
            return;
        }

        if (owner.getPlayStrategyFor(this) == DomPlayStrategy.silverGainer) {
            if (!owner.isGoingToBuyTopCardInBuyRules(owner.getTotalPotentialCurrency())) {
                if (!owner.isGoingToBuyTopCardInBuyRules(owner.getTotalPotentialCurrency().add(new DomCost(1, 0)))) {
                    owner.gain(DomCardName.Silver);
                    return;
                }
            } else {
                owner.gain(DomCardName.Silver);
                return;
            }
        }

        if (!playForTrashEstatesOrWorse())
         if (!playForMoney())
           if (!playForTrash())
              owner.gain(DomCardName.Silver);
    }

    private void handleHuman() {
        ArrayList<String> theOptions = new ArrayList<String>();
        theOptions.add("Trash a card");
        theOptions.add("+$1");
        theOptions.add("Gain a Silver");
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Select for Amulet", theOptions, "Mandatory!");
        if (theChoice == 1)
            owner.addAvailableCoins(1);
        if (theChoice == 2)
            owner.gain(DomCardName.Silver);
        if (theChoice == 0) {
            if (owner.getCardsInHand().size()>0) {
                ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
                owner.getEngine().getGameFrame().askToSelectCards("Choose a card to trash", owner.getCardsInHand(), theChosenCards, 1);
                for (DomCard theCard:theChosenCards) {
                    owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theCard.getName()).get(0)));
                }
            }
        }
    }

    private boolean playForTrashEstatesOrWorse() {
        ArrayList<DomCard> cardsInHand = owner.getCardsInHand();
        if (cardsInHand.isEmpty())
            return false;
        Collections.sort(cardsInHand,SORT_FOR_TRASHING);

        DomCard theCardToTrash = cardsInHand.get(0);

        if (theCardToTrash.getTrashPriority()<DomCardName.Copper.getTrashPriority(owner)) {
            owner.trash(owner.removeCardFromHand(theCardToTrash));
            return true;
        }

        if (!owner.getCardsFromHand(DomCardName.Market_Square).isEmpty() &&theCardToTrash.getTrashPriority()<=DomCardName.Copper.getTrashPriority(owner)) {
            owner.trash(owner.removeCardFromHand(theCardToTrash));
            return true;
        }

        return false;
    }

    private boolean playForAgroTrash() {
        ArrayList<DomCard> cardsInHand = owner.getCardsInHand();
        if (cardsInHand.isEmpty())
            return false;
        Collections.sort(cardsInHand,SORT_FOR_TRASHING);

        DomCard theCardToTrash = cardsInHand.get(0);

        if (theCardToTrash.getTrashPriority()<=DomCardName.Copper.getTrashPriority(owner)) {
            owner.trash(owner.removeCardFromHand(theCardToTrash));
            return true;
        }

        return false;
    }

    private boolean playForMoney() {
      if (owner.addingThisIncreasesBuyingPower(new DomCost(1, 0))) {
    	owner.addAvailableCoins(1);
        return true;
      }
      return false;
    }

    private boolean playForTrash() {
        ArrayList<DomCard> cardsInHand = owner.getCardsInHand();
        if (cardsInHand.isEmpty())
        	return false;
        Collections.sort(cardsInHand,SORT_FOR_TRASHING);

        DomCard theCardToTrash = cardsInHand.get(0);

        if (!owner.removingReducesBuyingPower(theCardToTrash)) {
            owner.trash(owner.removeCardFromHand(theCardToTrash));
            return true;
        }

        return false;
    }

    @Override
    public void resolveDuration() {
        play();
    }
}