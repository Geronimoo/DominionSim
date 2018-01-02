package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class IdolCard extends DomCard {
    public IdolCard() {
      super( DomCardName.Idol);
    }

    public void play() {
        owner.addAvailableCoins(2);
        if (owner.countInPlay(DomCardName.Idol)%2 == 0) {
            for (DomPlayer thePlayer : owner.getOpponents()) {
                if (!thePlayer.checkDefense()) {
                    thePlayer.gain(DomCardName.Curse);
                }
            }
        } else {
            //players may always react against attacks even when they are not attacking
            for (DomPlayer thePlayer : owner.getOpponents())
              thePlayer.checkDefense();
            owner.receiveBoon(null);
        }
    }
}