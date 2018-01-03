package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class CoppersmithCard extends DomCard {
    public CoppersmithCard () {
        super(DomCardName.Coppersmith);
    }

    public void play() {
        owner.increaseCoppersmithPlayedCounter();
        if (DomEngine.haveToLog)
            DomEngine.addToLog(this + "'s Coppers are now worth $" + (owner.getCoppersmithPlayedCount() + 1));
    }

    public boolean wantsToBePlayed() {
        boolean bePlayed = !owner.getCardsFromHand(DomCardName.Copper).isEmpty()
                || (!owner.getCardsFromHand(DomCardName.Counting_House).isEmpty()
                        && owner.getCardsFromHand(DomCardName.Counting_House).get(0).wantsToBePlayed());
        return bePlayed;
    }
}