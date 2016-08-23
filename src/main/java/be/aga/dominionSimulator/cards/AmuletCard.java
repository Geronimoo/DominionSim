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

        if (!playForMoney())
           if (!playForTrash())
              owner.gain(DomCardName.Silver);
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