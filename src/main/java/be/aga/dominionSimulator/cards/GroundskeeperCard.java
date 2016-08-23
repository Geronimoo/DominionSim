package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class GroundskeeperCard extends DomCard {
    public GroundskeeperCard() {
      super( DomCardName.Groundskeeper);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
    }
}