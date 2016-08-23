package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class SummonCard extends DomCard {
    public SummonCard() {
      super( DomCardName.Summon);
    }

    public void play() {
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
}