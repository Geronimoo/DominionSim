package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class TrainingCard extends DomCard {
    public TrainingCard() {
      super( DomCardName.Training);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
        } else {
            owner.placePlusOneCoinToken();
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
        owner.placePlusOneCoinToken(theChosenCard);
    }
}