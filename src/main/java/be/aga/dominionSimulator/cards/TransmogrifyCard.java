package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
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
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        Collections.sort( owner.getCardsInHand(), SORT_FOR_TRASHING);
        if (!owner.getCardsFromHand(DomCardName.Rats).isEmpty() && owner.count(DomCardName.Rats)>1) {
            owner.trash( owner.removeCardFromHand( owner.getCardsFromHand(DomCardName.Rats).get(0) ) );
            DomCardName theDesiredCard = owner.getDesiredCard(DomCardName.Rats.getCost(owner.getCurrentGame()).add(new DomCost(1,0 ))  , false);
            if (theDesiredCard!=null )
                owner.gainInHand(theDesiredCard);
            else
                owner.gainInHand(DomCardName.Copper);
            return;
        }
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

    private void handleHuman() {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
        owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
        owner.setNeedsToUpdateGUI();
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theChosenCard.getCost(owner.getCurrentGame()).add(new DomCost(1,0)).customCompare(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        if (theChooseFrom.size()==1) {
            owner.gainInHand(owner.getCurrentGame().takeFromSupply(theChooseFrom.get(0)));
        } else {
            owner.gainInHand(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Gain a card for " + this.getName().toString(), theChooseFrom, "Mandatory!")));
        }
    }
}