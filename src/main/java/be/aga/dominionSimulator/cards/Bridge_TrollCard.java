package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Bridge_TrollCard extends DomCard {
    public Bridge_TrollCard() {
      super( DomCardName.Bridge_Troll);
    }

    public void play() {
        owner.addAvailableBuys(1);
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (!thePlayer.checkDefense()) {
                thePlayer.activateMinusOneCoin();
            }
        }
    }

    public void resolveDuration() {
      owner.addAvailableBuys(1);
    }
}