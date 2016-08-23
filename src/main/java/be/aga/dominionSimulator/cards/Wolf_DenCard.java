package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Wolf_DenCard extends DomCard {
    public Wolf_DenCard() {
        super(DomCardName.Wolf_Den);
    }

    public static int countVP(DomPlayer aPlayer ){
        return -aPlayer.getDeck().countSingletonCards()*3;
    }
}