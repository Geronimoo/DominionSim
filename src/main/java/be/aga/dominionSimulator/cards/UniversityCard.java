package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class UniversityCard extends DomCard {
    public UniversityCard () {
      super( DomCardName.University);
    }

  public void play() {
    owner.addActions(2);
    if (owner.isHumanOrPossessedByHuman()) {
        handleHuman();
        return;
    }
    DomCardName theDesiredCard = owner.getDesiredCard(DomCardType.Action
    		                                               ,new DomCost(5,0) 
     													   ,false
     													   ,false
     													   ,null);
    if (theDesiredCard!=null)
	  owner.gain(theDesiredCard);
  }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theCard.hasCardType(DomCardType.Action) && new DomCost(5,0).customCompare(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for " + this.getName().toString(), theChooseFrom, "Don't gain anything");
        if (theChosenCard == null)
            return;
        owner.gain(owner.getCurrentGame().takeFromSupply(theChosenCard));
    }
}