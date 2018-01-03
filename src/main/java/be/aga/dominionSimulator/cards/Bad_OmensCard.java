package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class Bad_OmensCard extends DomCard {
    public Bad_OmensCard() {
      super( DomCardName.Bad_Omens);
    }

    public void play() {
      owner.putDeckInDiscard();
      if (owner.countInDeck(DomCardName.Copper)<2) {
         if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has less than 2 Coppers in deck");
         return;
      }
      owner.putOnTopOfDeck(owner.getDeck().removeFromDiscard(DomCardName.Copper));
      owner.putOnTopOfDeck(owner.getDeck().removeFromDiscard(DomCardName.Copper));
    }
}