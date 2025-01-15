package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Tide_PoolsCard extends DomCard {
    public Tide_PoolsCard() {
      super( DomCardName.Tide_Pools);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(3);
    }

    public void resolveDuration() {
      owner.doForcedDiscard(2,false);
    }
}