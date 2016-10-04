package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class ExpandCard extends DomCard {
    public ExpandCard () {
      super( DomCardName.Expand);
    }

    public void play() {
      if (owner.getCardsInHand().isEmpty())
    	return;
      DomCard theCardToTrash = owner.findCardToRemodel(this, 3);
      if (theCardToTrash==null) {
        //this is needed when card is played with Throne Room effect
        Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
        theCardToTrash=owner.getCardsInHand().get(0);
      }
      owner.trash(owner.removeCardFromHand( theCardToTrash));
      DomCost theMaxCostOfCardToGain = new DomCost( theCardToTrash.getCoinCost(owner.getCurrentGame()) + 3, theCardToTrash.getPotionCost());
	  DomCardName theDesiredCard = owner.getDesiredCard(theMaxCostOfCardToGain, false);
      if (theDesiredCard==null)
    	theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theMaxCostOfCardToGain);
      if (theDesiredCard!=null)
        owner.gain(theDesiredCard);
    }

    @Override
    public boolean wantsToBePlayed() {
      return owner.findCardToRemodel(this, 3)!=null;
   }
}