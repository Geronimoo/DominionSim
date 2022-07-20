package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Plateau_ShepherdsCard extends DomCard {
    public Plateau_ShepherdsCard() {
        super(DomCardName.Plateau_Shepherds);
    }

    public static int countVP(DomPlayer aPlayer ){
        int count = aPlayer.getDeck().countCardsThatCost(2);
        if (count>aPlayer.getFavors())
            count=aPlayer.getFavors();
        return count*2;
    }
}