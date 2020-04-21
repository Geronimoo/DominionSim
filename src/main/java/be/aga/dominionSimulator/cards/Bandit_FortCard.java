package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Bandit_FortCard extends DomCard {
    public Bandit_FortCard() {
        super(DomCardName.Bandit_Fort);
    }

    public static int countVP(DomPlayer aPlayer ){
        int theVP = 0;
        theVP-=aPlayer.count(DomCardName.Silver)*2;
        theVP-=aPlayer.count(DomCardName.Gold)*2;
        return theVP;
    }
}