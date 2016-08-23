package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class WallCard extends DomCard {
    public WallCard() {
        super(DomCardName.Wall);
    }

    public static int countVP(DomPlayer aPlayer ){
        int theCount = 15 - aPlayer.countAllCards();
        if (theCount>=0)
            return 0;
        return 15-aPlayer.countAllCards();
    }
}