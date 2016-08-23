package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.HashSet;

public class MuseumCard extends DomCard {
    public MuseumCard() {
        super(DomCardName.Museum);
    }

    public static int countVP(DomPlayer aPlayer ){
        return aPlayer.countDifferentCardsInDeck()*2;
    }
}