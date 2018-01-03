package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class Treasure_HunterCard extends TravellerCard {
    public Treasure_HunterCard() {
      super( DomCardName.Treasure_Hunter);
      myUpgrade=DomCardName.Warrior;
    }

    public void play() {
      owner.addActions(1);
      owner.addAvailableCoins(1);
      if (owner.getOpponents().isEmpty())
    	return;
      DomPlayer theRightOpponent = owner.getOpponents().get(owner.getOpponents().size()-1);
      ArrayList<DomCardName> cardsGainedLastTurn = theRightOpponent.getCardsGainedLastTurn();
      for (int i=0;i<cardsGainedLastTurn.size();i++){
          owner.gain(DomCardName.Silver);
      }
    }
}