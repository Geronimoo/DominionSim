package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class SleighCard extends DomCard {
    public SleighCard() {
      super( DomCardName.Sleigh);
    }

    public void play() {
        DomCard theHorse = owner.getCurrentGame().takeFromSupply(DomCardName.Horse);
        if (theHorse!=null)
            owner.gain(theHorse);
        theHorse = owner.getCurrentGame().takeFromSupply(DomCardName.Horse);
        if (theHorse!=null)
            owner.gain(theHorse);
    }

    public boolean wantsToReact(DomCard aCard) {
        if (aCard.getDiscardPriority(owner.getActionsAndVillagersLeft())> DomCardName.Copper.getDiscardPriority(1) )
          return true;
        return false;
    }
}