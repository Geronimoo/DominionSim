package be.aga.dominionSimulator.cards;

import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

public class RemakeCard extends DomCard {

    public RemakeCard () {
      super( DomCardName.Remake);
    }

    public void play() {
        int theTrashCount=0;
        Collections.sort( owner.getCardsInHand(), SORT_FOR_TRASHING);
        while (theTrashCount<2 && !owner.getCardsInHand().isEmpty()) {
            DomCard theCardToTrash = owner.removeCardFromHand( owner.getCardsInHand().get( 0 ) ); 
            owner.trash( theCardToTrash );
            theTrashCount++;
            DomCardName theDesiredCard = owner.getDesiredCard(new DomCost( theCardToTrash.getCoinCost(owner.getCurrentGame()) + 1, theCardToTrash.getPotionCost()), true);
            if (theDesiredCard==null) {
              //it's mandatory to gain a card
              DomCost theCost = theCardToTrash.getCost(owner.getCurrentGame()).add(new DomCost(1,0));
  			  theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theCost,true);
            }
			if (theDesiredCard!=null)
			  owner.gain(theDesiredCard);
        }
    }

	@Override
    public boolean wantsToBePlayed() {
        int theTrashCount=0;
        for (DomCard theCard : owner.getCardsInHand()) {
          if (theCard!=this)
            theTrashCount+=theCard.getTrashPriority()<20 ? 1 : 0;
        }
        return theTrashCount>=2;
   }
}