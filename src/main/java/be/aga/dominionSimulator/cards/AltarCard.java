package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class AltarCard extends DomCard {
    public AltarCard() {
      super( DomCardName.Altar);
    }

    public void play() {
      if (!owner.getCardsInHand().isEmpty()) {
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        owner.trash(owner.removeCardFromHand( owner.getCardsInHand().get( 0 ) ));
      }
      DomCardName theDesiredCard = owner.getDesiredCard(new DomCost( 5, 0), false);
      if (theDesiredCard==null) {
         theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(5, 0));
      }
      if (theDesiredCard!=null)
         owner.gain(theDesiredCard);
    }
}