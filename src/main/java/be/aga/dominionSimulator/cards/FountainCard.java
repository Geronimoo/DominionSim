package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class FountainCard extends DomCard {
    public FountainCard() {
        super(DomCardName.Fountain);
    }

    public static int countVP(DomPlayer aPlayer ){
        return aPlayer.countInDeck(DomCardName.Copper)>=10 ? 15 : 0;
    }
}