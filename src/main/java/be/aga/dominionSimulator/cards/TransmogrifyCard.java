package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class TransmogrifyCard extends DomCard {
    public TransmogrifyCard() {
      super( DomCardName.Transmogrify);
    }

    public void play() {
        owner.addActions(1);
        if (owner.getCardsFromPlay(DomCardName.Transmogrify).contains(this))
            owner.putOnTavernMat(owner.removeCardFromPlay(this));
    }

    public boolean wantsToBeCalled() {
        Collections.sort( owner.getCardsInHand(), SORT_FOR_TRASHING);
        if (owner.getCardsInHand().isEmpty())
            return false;
        if (owner.getCardsInHand().get(0).getName()==DomCardName.Curse)
            return true;
        for (DomCard theCard : owner.getCardsInHand()) {
            DomCardName theDesiredCard = owner.getDesiredCard(theCard.getName().getCost(owner.getCurrentGame()).add(new DomCost(1, 0)), false);
            if (theDesiredCard != null && theDesiredCard.getTrashPriority(owner) > theCard.getTrashPriority()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doWhenCalled() {
        if (owner.getCardsInHand().isEmpty())
        	return;
        Collections.sort( owner.getCardsInHand(), SORT_FOR_TRASHING);
        if (owner.getCardsInHand().get(0).getName()==DomCardName.Curse) {
            owner.trash( owner.removeCardFromHand( owner.getCardsInHand().get(0) ) );
            DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(1,0 ) , false);
            if (theDesiredCard!=null )
                owner.gainInHand(theDesiredCard);
            else
                owner.gainInHand(DomCardName.Copper);
            return;
        }

        for (DomCard theCard : owner.getCardsInHand()) {
          DomCardName theDesiredCard = owner.getDesiredCard(theCard.getName().getCost(owner.getCurrentGame()).add(new DomCost(1,0 ) ), false);
          if (theDesiredCard!=null && theDesiredCard.getTrashPriority(owner)>theCard.getTrashPriority()) {
            owner.trash( owner.removeCardFromHand( theCard ) );
            owner.gainInHand(theDesiredCard);
            return;
          }
        }
    }
}