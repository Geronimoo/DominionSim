package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class SmugglersCard extends DomCard {
    public SmugglersCard () {
      super( DomCardName.Smugglers);
    }

    public void play() {
      if (owner.getOpponents().isEmpty())
    	return;
      DomPlayer theRightOpponent = owner.getOpponents().get(owner.getOpponents().size()-1);
      ArrayList<DomCardName> cardsGainedLastTurn = theRightOpponent.getCardsGainedLastTurn();
      Collections.sort(cardsGainedLastTurn,DomCardName.SORT_FOR_TRASHING);
      for (int i=cardsGainedLastTurn.size()-1;i>=0;i--){
    	if (  new DomCost(6, 0).compareTo(cardsGainedLastTurn.get(i).getCost(owner.getCurrentGame())) >= 0 
    	 && owner.wants(cardsGainedLastTurn.get(i))){
          owner.gain(cardsGainedLastTurn.get(i));
    	  return;
    	}
      }
    }
}