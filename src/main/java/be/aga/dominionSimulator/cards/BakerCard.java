package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class BakerCard extends DomCard {
    public BakerCard() {
      super( DomCardName.Baker);
    }

    public void play() {
      owner.addActions(1);
      owner.addCoinTokens(1);
      owner.drawCards(1);
    }
}