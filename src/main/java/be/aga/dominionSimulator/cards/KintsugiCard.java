package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class KintsugiCard extends DomCard {

	public KintsugiCard() {
      super( DomCardName.Kintsugi);
    }

    public void play() {
        if (owner.count(DomCardName.Gold)>0) {
            DomCard theCard = owner.findCardToRemodel(null, 2, true);
            if (theCard!=null) {
                owner.trash(owner.removeCardFromHand(theCard));
                DomCost theMaxCostOfCardToGain = new DomCost( theCard.getCoinCost(owner.getCurrentGame()) + 2, theCard.getPotionCost());
                DomCardName theDesiredCard = owner.getDesiredCard(theMaxCostOfCardToGain, false);
                if (theDesiredCard==null)
                    theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theMaxCostOfCardToGain);
                if (theDesiredCard!=null)
                    owner.gain(theDesiredCard);

            }
        } else {
            Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
            owner.trash(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
        }
    }
}