package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class DismantleCard extends DomCard {

    public DismantleCard() {
      super( DomCardName.Dismantle);
    }

    public void play() {
        if (owner.getCardsInHand().isEmpty())
        	return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        Collections.sort( owner.getCardsInHand(), SORT_FOR_TRASHING);
        DomCard theCardToTrash = owner.getCardsInHand().get(0);
        owner.trash(owner.removeCardFromHand(theCardToTrash));
        if (theCardToTrash.getCoinCost(owner.getCurrentGame())>0) {
            DomCost theDownCost = theCardToTrash.getName().getCost(owner.getCurrentGame()).add(new DomCost(-1,0 )) ;
            DomCardName theDesiredDownCard = owner.getDesiredCard(theDownCost, false);
            if (theDesiredDownCard==null)
              theDesiredDownCard= owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theDownCost, false);
            owner.gain(theDesiredDownCard);
            owner.gain(DomCardName.Gold);
        }
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
        owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
        if (theChosenCard.getCoinCost(owner.getCurrentGame())>0) {
            theChooseFrom = new ArrayList<DomCardName>();
            for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
                if (theChosenCard.getCost(owner.getCurrentGame()).add(new DomCost(-1, 0)).customCompare(theCard.getCost(owner.getCurrentGame())) >= 0 && owner.getCurrentGame().countInSupply(theCard) > 0)
                    theChooseFrom.add(theCard);
            }
            if (theChooseFrom.isEmpty())
                return;
            if (theChooseFrom.size() == 1) {
                owner.gain(owner.getCurrentGame().takeFromSupply(theChooseFrom.get(0)));
            } else {
                DomCardName theFirstChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Gain a card for " + this.getName().toString(), theChooseFrom, "Mandatory!");
                owner.gain(owner.getCurrentGame().takeFromSupply(theFirstChosenCard));
            }
            owner.gain(DomCardName.Gold);
        }
    }

    @Override
    public boolean wantsToBePlayed() {
        for ( int i=0; i<owner.getCardsInHand().size();i++) {
            DomCard theCard = owner.getCardsInHand().get(i);
        	if (theCard==this)
        	  continue;
            if (theCard.getTrashPriority()<20 && !owner.removingReducesBuyingPower(theCard))
              return true;
//            DomCardName theDesiredDownCard = owner.getDesiredCard(theCard.getName().getCost(owner.getCurrentGame()).add(new DomCost(-1,0 ) ), false);
//            if (theDesiredDownCard!=null
//             && theDesiredDownCard.getTrashPriority(owner)>=theCard.getTrashPriority())
//            	return true;
        }
        return false;
   }
}