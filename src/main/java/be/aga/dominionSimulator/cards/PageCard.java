package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.enums.DomCardName;

public class PageCard extends TravellerCard {
    public PageCard() {
      super( DomCardName.Page);
      myUpgrade=DomCardName.Treasure_Hunter;
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
    }
}