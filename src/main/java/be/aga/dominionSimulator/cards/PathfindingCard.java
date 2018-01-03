package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class PathfindingCard extends DomCard {

	public PathfindingCard() {
      super( DomCardName.Pathfinding);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
        } else {
            owner.placePlusOneCardToken();
        }
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theCard.hasCardType(DomCardType.Action))
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select a card", theChooseFrom, "Mandatory!");
        owner.placePlusOneCardToken(theChosenCard);
    }
}