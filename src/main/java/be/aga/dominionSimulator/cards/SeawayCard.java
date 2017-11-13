package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class SeawayCard extends DomCard {

	public SeawayCard() {
      super( DomCardName.Seaway);
    }

    public void play() {
	    if (owner.isHumanOrPossessedByHuman()) {
	        handleHuman();
        } else {
            owner.gain(owner.placePlusOneBuyToken());
        }
   }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
            if (theCard.hasCardType(DomCardType.Action) && new DomCost(4,0).compareTo(theCard.getCost(owner.getCurrentGame()))>=0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select a card", theChooseFrom, "Mandatory!");
        owner.placePlusOneBuyToken(theChosenCard);
        owner.gain(theChosenCard);
    }
}