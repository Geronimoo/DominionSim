package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class InnkeeperCard extends DomCard {
    public InnkeeperCard() {
      super( DomCardName.Innkeeper);
    }

    public void play() {
        owner.addActions(1);
        if (owner.getDeckAndDiscardSize()<2) {
            owner.drawCards(1);
            return;
        }
        if (owner.count(DomCardName.Tunnel)==0 || owner.countInPlay(DomCardName.Innkeeper)>1) {
            owner.drawCards(3);
            owner.doForcedDiscard(3, false);
        } else {
            owner.drawCards(5);
            owner.doForcedDiscard(6, false);
        }
    }
}