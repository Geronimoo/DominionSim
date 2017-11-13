package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class SummonCard extends DomCard {
    public SummonCard() {
      super( DomCardName.Summon);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      DomCardName theDesiredCard = owner.getDesiredCard(DomCardType.Action, new DomCost( 4, 0), false ,false,null);
      if (theDesiredCard==null) {
    	theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor( owner, DomCardType.Action,new DomCost(4, 0));
      }
      if (theDesiredCard!=null) {
          DomCard theCard = owner.getCurrentGame().takeFromSupply(theDesiredCard);
          owner.gain(theCard);
          if (owner.getTopOfDiscard()!=theCard) {
              if (DomEngine.haveToLog) DomEngine.addToLog( this + " has lost track of " + theCard +", so will not have an effect");
              return;
          }
          owner.setAsideToSummon(owner.removeCardFromDiscard(theCard));
      }
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
            if (theCard.hasCardType(DomCardType.Action) && new DomCost(4,0).compareTo(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for " + this.getName().toString(), theChooseFrom, "Mandatory!");
        DomCard theCardToGain = owner.getCurrentGame().takeFromSupply(theChosenCard);
        owner.gain(theCardToGain);
        if (owner.getTopOfDiscard()!=theCardToGain) {
            if (DomEngine.haveToLog) DomEngine.addToLog( this + " has lost track of " + theCardToGain +", so will not have an effect");
            return;
        }
        owner.setAsideToSummon(owner.removeCardFromDiscard(theCardToGain));
    }
}