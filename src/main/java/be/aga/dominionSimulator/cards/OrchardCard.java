package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.HashSet;

public class OrchardCard extends DomCard {
    public OrchardCard() {
        super(DomCardName.Orchard);
    }

    public static int countVP(DomPlayer aPlayer ){
        int theCount = 0;
        for (DomCardName theCard : aPlayer.getDeck().keySet()) {
            if (theCard.hasCardType(DomCardType.Action) && aPlayer.countInDeck(theCard)>=3)
                theCount+=4;
        }
        return theCount;
    }
}