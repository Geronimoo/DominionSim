package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.HashSet;

public class Bandit_FortCard extends DomCard {
    public Bandit_FortCard() {
        super(DomCardName.Bandit_Fort);
    }

    public static int countVP(DomPlayer aPlayer ){
        int theVP = 0;
        theVP-=aPlayer.countInDeck(DomCardName.Silver)*2;
        theVP-=aPlayer.countInDeck(DomCardName.Gold)*2;
        return theVP;
    }
}