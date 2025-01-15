package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Gold_MineCard extends DomCard {
    public Gold_MineCard() {
      super( DomCardName.Gold_Mine);
    }

    public void play() {
        owner.addActions(1);
        owner.drawCards(1);
        owner.addAvailableBuys(1);
        if ((owner.stillInEarlyGame() && owner.getCurrentGame().countInSupply(DomCardName.Gold)>0) || owner.count(DomCardName.Tanuki)>0) {
            owner.gain(DomCardName.Gold);
            owner.addDebt(4);
        }
    }
}