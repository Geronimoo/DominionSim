package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class FamiliarCard extends DomCard {
    public FamiliarCard () {
      super( DomCardName.Familiar);
    }

    public void play() {
    	owner.addActions(1);
    	owner.drawCards(1);
        for (DomPlayer thePlayer : owner.getOpponents()) {
          if (!thePlayer.checkDefense()) {
            thePlayer.gain(DomCardName.Curse);
          }
        }
    }
}