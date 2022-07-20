package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class ImporterCard extends DomCard {
    public ImporterCard() {
      super( DomCardName.Importer);
    }

    public void resolveDuration() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        DomCardName theDesiredCard = owner.getDesiredCard(new DomCost( 5, 0), false);
        if (theDesiredCard==null) {
            theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(5, 0));
        }
        if (theDesiredCard!=null)
            owner.gain(theDesiredCard);
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (new DomCost(5,0).customCompare(theCard.getCost(owner.getCurrentGame()))>=0 )
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!")));
    }

}