package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class MasterpieceCard extends DomCard {
    public MasterpieceCard() {
      super( DomCardName.Masterpiece);
    }

    public void doWhenBought(DomPlayer player) {
        for (int i=0;i<player.getTotalAvailableCoins();i++) {
            player.gain(DomCardName.Silver);
        }
        player.setAvailableCoins(0);
        player.setCoinTokens(0);
    }
}