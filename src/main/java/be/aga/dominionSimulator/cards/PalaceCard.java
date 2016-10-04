package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class PalaceCard extends DomCard {
    public PalaceCard() {
        super(DomCardName.Palace);
    }

    public static int countVP(DomPlayer aPlayer ){
        int theMinCount = aPlayer.countInDeck(DomCardName.Copper);
        int theCount = aPlayer.countInDeck(DomCardName.Silver);
        if (theCount<theMinCount)
            theMinCount=theCount;
        theCount = aPlayer.countInDeck(DomCardName.Gold);
        if (theCount<theMinCount)
            theMinCount=theCount;
        return theMinCount*3;
    }
}