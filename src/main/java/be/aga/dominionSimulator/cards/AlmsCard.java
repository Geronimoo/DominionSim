package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class AlmsCard extends DomCard {

	public AlmsCard() {
      super( DomCardName.Alms);
    }

    public void play() {
	    if (owner.isAlmsActivated())
	        return;
        owner.setAlmsActivated();
        if (owner.countInPlay(DomCardType.Treasure)>0)
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
                if (new DomCost(4,0).compareTo(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0 && !theCard.hasCardType(DomCardType.Event))
                    theChooseFrom.add(theCard);
            }
            if (theChooseFrom.isEmpty())
                return;
            owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!")));
        } else {
            DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(4, 0), false);
            if (theDesiredCard != null)
                owner.gain(theDesiredCard);
        }
    }
}