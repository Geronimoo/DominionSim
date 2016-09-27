package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class PoacherCard extends DomCard {
    public PoacherCard() {
      super( DomCardName.Poacher);
    }

    public void play() {
      owner.addActions(1);
      owner.addAvailableCoins(1);
      owner.drawCards(1);
      owner.doForcedDiscard(owner.getCurrentGame().countEmptyPiles(),false);
    }
}