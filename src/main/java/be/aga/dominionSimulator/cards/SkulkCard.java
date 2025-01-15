package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class SkulkCard extends DomCard {
    public SkulkCard() {
      super( DomCardName.Skulk);
    }

    public void play() {
        owner.addAvailableBuys(1);
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (!thePlayer.checkDefense()) {
                thePlayer.receiveHex(null);
            }
        }
    }

    @Override
    public void doWhenGained() {
        owner.gain(DomCardName.Gold);
    }

    @Override
    public int getTrashPriority() {
        return 14;
    }
}