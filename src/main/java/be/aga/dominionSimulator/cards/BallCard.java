package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class BallCard extends DomCard {

	public BallCard() {
      super( DomCardName.Ball);
    }

    public void play() {
        possiblyGainCard();
        possiblyGainCard();
        owner.activateMinusOneCoin();
    }

    private void possiblyGainCard() {
        DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(4, 0), false);
        if (theDesiredCard==null)
            theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(4, 0));
        if (theDesiredCard==null)
            return;
        owner.gain(theDesiredCard);
    }
}