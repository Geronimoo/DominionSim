package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class Lost_ArtsCard extends DomCard {

	public Lost_ArtsCard() {
      super( DomCardName.Lost_Arts);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
        } else {
            owner.placePlusOneActionToken();
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
        owner.placePlusOneActionToken(theChosenCard);
    }
}