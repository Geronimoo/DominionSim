package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class Treasure_HunterCard extends DomCard {
    public Treasure_HunterCard() {
      super( DomCardName.Treasure_Hunter);
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

    @Override
    public void handleCleanUpPhase() {
        if (owner==null)
            return;
        if (owner.wants(DomCardName.Warrior)) {
            DomPlayer theOwner = owner;
            owner.returnToSupply(this);
            theOwner.gain(DomCardName.Warrior);
            return;
        }
        super.handleCleanUpPhase();
    }
}