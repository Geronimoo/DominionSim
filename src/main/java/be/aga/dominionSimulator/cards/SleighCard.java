package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;

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
        if (aCard.getDiscardPriority(owner.getActionsLeft())> DomCardName.Copper.getDiscardPriority(1) )
          return true;
        return false;
    }
}