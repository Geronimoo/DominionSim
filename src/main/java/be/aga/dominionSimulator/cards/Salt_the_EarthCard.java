package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class Salt_the_EarthCard extends DomCard {
    public Salt_the_EarthCard() {
      super( DomCardName.Salt_the_Earth);
    }

    public void play() {
        owner.addVP(1);
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        DomCardName theCard = null;
        theCard = DomCardName.Estate;
//        theCard = DomCardName.Province;
        if (owner.countVictoryPoints()>owner.countMaxOpponentsVictoryPoints())
            theCard = DomCardName.Province;
        if (owner.getCurrentGame().countInSupply(DomCardName.Duke)>0 && owner.getCurrentGame().countInSupply(DomCardName.Duchy)>0)
            theCard = DomCardName.Duchy;
        if (owner.getCurrentGame().countInSupply(DomCardName.Feodum)>0)
            theCard = DomCardName.Feodum;
        if (owner.getCurrentGame().countInSupply(DomCardName.Crumbling_Castle)>0)
            theCard = DomCardName.Crumbling_Castle;
        DomCard theCardToTrash = owner.getCurrentGame().takeFromSupply(theCard);
        if (theCardToTrash==null) {
            theCard = owner.getCurrentGame().getBestCardInSupplyFor(owner,DomCardType.Victory,new DomCost(10000,10000),false);
            if (theCard==null)
                return;
            theCardToTrash = owner.getCurrentGame().takeFromSupply(theCard);
        }
//        theCardToTrash.setOwner(owner);
        owner.trash(theCardToTrash);
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theCard.hasCardType(DomCardType.Victory) && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
        owner.trash(owner.getCurrentGame().takeFromSupply(theChosenCard));
    }
}