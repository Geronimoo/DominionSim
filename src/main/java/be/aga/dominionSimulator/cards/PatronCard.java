package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class PatronCard extends DomCard {
    public PatronCard() {
      super( DomCardName.Patron);
    }

    public void play() {
      owner.addAvailableCoins(2);
      owner.addVillagers(1);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

    @Override
    public boolean react() {
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + this);
        owner.addCoffers(1);
        return true;
    }
}