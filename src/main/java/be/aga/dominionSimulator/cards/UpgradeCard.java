package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class UpgradeCard extends DomCard {
    public UpgradeCard () {
      super( DomCardName.Upgrade);
    }

    public void play() {
        owner.addActions( 1 );
        owner.drawCards( 1 );
        if (owner.getCardsInHand().isEmpty())
        	return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        if (!owner.getCardsFromHand(DomCardName.Fortress).isEmpty()
                &&owner.getDesiredCard(DomCardName.Fortress.getCost(owner.getCurrentGame()).add(new DomCost(1,0 ) ), true)!=null) {
            DomCard theCard = owner.getCardsFromHand(DomCardName.Fortress).get(0);
            DomCardName theDesiredCard = owner.getDesiredCard(theCard.getName().getCost(owner.getCurrentGame()).add(new DomCost(1,0 ) ), true);
            if (theDesiredCard!=null && theDesiredCard.getTrashPriority(owner)>=theCard.getTrashPriority()) {
                owner.trash( owner.removeCardFromHand( theCard ) );
                owner.gain(theDesiredCard);
                return;
            }
        }
            
        Collections.sort( owner.getCardsInHand(), SORT_FOR_TRASHING);
        if (owner.getCardsInHand().get(0).getName()==DomCardName.Curse || owner.getCardsInHand().get(0).getName()==DomCardName.Copper || owner.getCardsInHand().get(0).hasCardType(DomCardType.Ruins)) {
            owner.trash( owner.removeCardFromHand( owner.getCardsInHand().get(0) ) );
            DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(1,0 ) , true);
            if (theDesiredCard!=null )
                owner.gain(theDesiredCard);
            return;
        }

        for (DomCard theCard : owner.getCardsInHand()) {
          DomCardName theDesiredCard = owner.getDesiredCard(theCard.getName().getCost(owner.getCurrentGame()).add(new DomCost(1,0 ) ), true);
          if (theDesiredCard!=null && theDesiredCard.getTrashPriority(owner)>=theCard.getTrashPriority()) {
            owner.trash( owner.removeCardFromHand( theCard ) );
            owner.gain(theDesiredCard);
            return;
          }
        }
        //if nothing to gain, trash the worst card anyway
        DomCard theCardToTrash = owner.removeCardFromHand( owner.getCardsInHand().get( 0 ) );
		DomCost theCost = new DomCost( theCardToTrash.getCoinCost(owner.getCurrentGame()) + 1, theCardToTrash.getPotionCost());
        owner.trash( theCardToTrash );
        DomCardName theCardToGain = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theCost,true);
        if (theCardToGain!=null)
		  owner.gain(theCardToGain);
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
            if (theCard.getCost(owner.getCurrentGame()).customCompare(theChosenCard.getCost(owner.getCurrentGame()).add(new DomCost(1,0)))==0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        if (theChooseFrom.size()==1) {
            owner.gain(owner.getCurrentGame().takeFromSupply(theChooseFrom.get(0)));
        } else {
            owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Gain a card for " + this.getName().toString(), theChooseFrom, "Mandatory!")));
        }
    }
}