package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class OasisCard extends DomCard {
    public OasisCard () {
      super( DomCardName.Oasis);
    }

    public void play() {
      owner.addActions(1);
      owner.addAvailableCoins(1);
      owner.drawCards(1);
      owner.doForcedDiscard(1, false);
    }
}