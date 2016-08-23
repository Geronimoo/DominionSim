package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.HashSet;

public class TowerCard extends DomCard {
    public TowerCard() {
        super(DomCardName.Tower);
    }

    public static int countVP(DomPlayer aPlayer ){
        int theCount = 0;
        for (DomCardName theCard : aPlayer.getDeck().keySet()) {
            if (theCard.hasCardType(DomCardType.Victory))
                continue;
            if (aPlayer.getCurrentGame().countInSupply(theCard)>0)
                continue;
            theCount+=aPlayer.countInDeck(theCard);
        }
        return theCount;
    }
}