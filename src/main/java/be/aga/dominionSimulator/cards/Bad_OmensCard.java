package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class Bad_OmensCard extends DomCard {
    public Bad_OmensCard() {
      super( DomCardName.Bad_Omens);
    }

    public void play() {
      owner.putDeckInDiscard();
      if (owner.count(DomCardName.Copper)<2) {
         if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has less than 2 Coppers in deck");
         if (owner.count(DomCardName.Patron)>0)
             owner.addCoffers(owner.count(DomCardName.Patron));
      }
      DomCard theCopper = owner.getDeck().removeFromDiscard(DomCardName.Copper);
      if (theCopper!=null)
        owner.putOnTopOfDeck(theCopper);
      theCopper = owner.getDeck().removeFromDiscard(DomCardName.Copper);
        if (theCopper!=null)
            owner.putOnTopOfDeck(theCopper);
    }
}