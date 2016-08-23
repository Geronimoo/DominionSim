package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class CoppersmithCard extends DomCard {
    public CoppersmithCard () {
      super( DomCardName.Coppersmith);
    }

    public void play() {
        if (DomEngine.haveToLog) DomEngine.addToLog( this + "'s Coppers are now worth $" + (owner.getCardsFromPlay(DomCardName.Coppersmith).size()+1) );
    }

    @Override
    public double getPotentialCoinValue() {
      if (owner.getActionsLeft()==0 
       && owner.getCardsInHand().contains( this ) ) {
          return 0;
      }
      return owner.getCardsFromHand( DomCardName.Copper ).size();
    }

    public boolean wantsToBePlayed() {
      return !owner.getCardsFromHand( DomCardName.Copper).isEmpty();
    }
}