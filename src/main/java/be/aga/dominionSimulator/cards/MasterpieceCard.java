package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class MasterpieceCard extends DomCard {
    public MasterpieceCard() {
      super( DomCardName.Masterpiece);
    }

    public void doWhenBought() {
        for (int i=0;i<owner.getTotalAvailableCoins();i++) {
            owner.gain(DomCardName.Silver);
        }
        owner.setAvailableCoins(0);
        owner.setCoinTokens(0);
    }

}