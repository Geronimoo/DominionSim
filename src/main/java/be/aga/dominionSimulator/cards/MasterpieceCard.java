package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class MasterpieceCard extends DomCard {
    public MasterpieceCard() {
      super( DomCardName.Masterpiece);
    }

    public void doWhenBought(DomPlayer player) {
        if (owner.isHumanOrPossessedByHuman()) {
            doHumanWhenBought();
            return;
        }
        for (int i=0;i<player.getTotalAvailableCoins();i++) {
            player.gain(DomCardName.Silver);
        }
        player.setAvailableCoins(0);
        player.spendCoinTokens(player.getCoinTokens());
    }

    private void doHumanWhenBought() {
        ArrayList<String> theOptions = new ArrayList<String>();
        for (int i = 1; i <= owner.getAvailableCoinsWithoutTokens(); i++) {
            theOptions.add("Overpay $" + i);
        }
        int theOverpayAmount = owner.getEngine().getGameFrame().askToSelectOption("Overpay?", theOptions, "Don't overpay");
        if (theOverpayAmount == -1)
            return;
        if (DomEngine.haveToLog)
            DomEngine.addToLog(owner + " overpays $" + (theOverpayAmount+1));
        owner.addAvailableCoins(-(theOverpayAmount + 1));
        for (int i = 1; i <= theOverpayAmount + 1; i++) {
            owner.gain(DomCardName.Silver);
        }
    }
}