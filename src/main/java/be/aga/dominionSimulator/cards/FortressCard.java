package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class FortressCard extends DomCard {
    public FortressCard() {
      super( DomCardName.Fortress);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(1);
    }
}