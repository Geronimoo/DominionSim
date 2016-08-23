package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class ConquestCard extends DomCard {
    public ConquestCard() {
      super( DomCardName.Conquest);
    }

    public void play() {
      owner.gain(DomCardName.Silver);
      owner.gain(DomCardName.Silver);
        int theCount = 0;
      for (DomCardName theCard : owner.getCardsGainedLastTurn()){
          if (theCard==DomCardName.Silver)
              theCount++;
      }
      owner.addVP(theCount);
    }
}