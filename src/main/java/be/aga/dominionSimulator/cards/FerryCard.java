package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class FerryCard extends DomCard {

	public FerryCard() {
      super( DomCardName.Ferry);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()){
            handleHuman();
        } else {
            owner.placeMinus$2Token();
        }
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
            if (theCard.hasCardType(DomCardType.Action))
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select a card", theChooseFrom, "Mandatory!");
        owner.placeMinus$2Token(theChosenCard);
    }
}