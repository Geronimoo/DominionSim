package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class Dame_NatalieCard extends KnightCard {

    public Dame_NatalieCard() {
        super(DomCardName.Dame_Natalie);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
                if (new DomCost(3,0).compareTo(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0)
                    theChooseFrom.add(theCard);
            }
            if (theChooseFrom.isEmpty())
                return;
            owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Don't gain")));
        } else {
            DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(3, 0), false);
            if (theDesiredCard != null)
                owner.gain(theDesiredCard);
        }
        super.play();
    }
}