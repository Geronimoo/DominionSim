package be.aga.dominionSimulator.cards;

import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

public class UpgradeCard extends DomCard {
    public UpgradeCard () {
      super( DomCardName.Upgrade);
    }

    public void play() {
        owner.addActions( 1 );
        owner.drawCards( 1 );
        if (owner.getCardsInHand().isEmpty())
        	return;
        Collections.sort( owner.getCardsInHand(), SORT_FOR_TRASHING);
        if (owner.getCardsInHand().get(0).getName()==DomCardName.Curse || owner.getCardsInHand().get(0).getName()==DomCardName.Copper) {
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
}