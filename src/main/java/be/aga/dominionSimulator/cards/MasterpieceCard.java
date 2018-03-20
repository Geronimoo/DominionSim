package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
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
        } else {
            if (player.getCoinTokens()==0 && player.getTotalAvailableCoins()>20 && player.getCurrentGame().getBoard().get(DomCardName.Triumph)!=null) {
                double toSpend = 4.0 * (player.getTotalAvailableCoins()+3) / 9 - 3;
                int theRound = (int) (toSpend/5);
                int theOverpay = theRound * 5 + 5;
                if (DomEngine.haveToLog)
                    DomEngine.addToLog(player + " overpays $" + theOverpay);
                for (int i = 0; i < theOverpay; i++) {
                    player.gain(DomCardName.Silver);
                }
                player.setAvailableCoins(player.getTotalAvailableCoins()-theOverpay);
            } else {
                if (DomEngine.haveToLog)
                    DomEngine.addToLog(player + " overpays $" + player.getTotalAvailableCoins());
                for (int i = 0; i < player.getTotalAvailableCoins(); i++) {
                    player.gain(DomCardName.Silver);
                }
                player.setAvailableCoins(0);
                player.spendCoinTokens(player.getCoinTokens());
            }
        }
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