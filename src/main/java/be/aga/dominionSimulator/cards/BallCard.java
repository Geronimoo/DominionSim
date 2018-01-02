package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import java.util.ArrayList;

public class BallCard extends DomCard {

	public BallCard() {
      super( DomCardName.Ball);
    }

    public void play() {
	    if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            handleHuman();
        } else {
            possiblyGainCard();
            possiblyGainCard();
        }
        owner.activateMinusOneCoin();
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
            if (new DomCost(4,0).compareTo(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0 )
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for " + this.getName().toString(), theChooseFrom, "Don't gain");
        if (theChosenCard==null)
            return;
        owner.gain(owner.getCurrentGame().takeFromSupply(theChosenCard));
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