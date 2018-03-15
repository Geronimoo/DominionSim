package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPhase;

import java.util.ArrayList;

public class WerewolfCard extends DomCard {
    public WerewolfCard() {
      super( DomCardName.Werewolf);
    }

    public void play() {
        if (owner.getPhase()== DomPhase.Action) {
            owner.drawCards(3);
        }
        if (owner.getPhase()==DomPhase.Night) {
            for (DomPlayer thePlayer : owner.getOpponents()) {
                if (!thePlayer.checkDefense()) {
                    thePlayer.receiveHex(null);
                }
            }
        }
    }
}