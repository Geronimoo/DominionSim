package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class SettlersCard extends DomCard {
    public SettlersCard() {
      super( DomCardName.Settlers);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      DomCard theCopper = owner.removeFromDiscard(DomCardName.Copper);
      if (theCopper!=null)
        owner.putInHand(theCopper);
    }
}