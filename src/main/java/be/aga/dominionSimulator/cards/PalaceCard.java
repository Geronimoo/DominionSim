package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class PalaceCard extends DomCard {
    public PalaceCard() {
        super(DomCardName.Palace);
    }

    public static int countVP(DomPlayer aPlayer ){
        int theMinCount = aPlayer.count(DomCardName.Copper);
        int theCount = aPlayer.count(DomCardName.Silver);
        if (theCount<theMinCount)
            theMinCount=theCount;
        theCount = aPlayer.count(DomCardName.Gold);
        if (theCount<theMinCount)
            theMinCount=theCount;
        return theMinCount*3;
    }
}