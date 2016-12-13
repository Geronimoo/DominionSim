package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class ScavengerCard extends DomCard {
    public ScavengerCard() {
      super( DomCardName.Scavenger);
    }

    public void play() {
      owner.addAvailableCoins(2);
      owner.putDeckInDiscard();
      ArrayList<DomCard> theCardsToConsider = owner.getCardsFromDiscard();
      if (theCardsToConsider.isEmpty())
          return;
      Collections.sort(theCardsToConsider, SORT_FOR_DISCARDING);
      DomCard theChosenCard = theCardsToConsider.get(theCardsToConsider.size() - 1);
      if (owner.countInDeck(DomCardName.Stash)>0) {
          for (DomCard theCard : theCardsToConsider){
              if (theCard.getName()==DomCardName.Scavenger)
                  theChosenCard=theCard;
          }
      }
      owner.putOnTopOfDeck(owner.removeCardFromDiscard(theChosenCard));
    }

    @Override
    public int getPlayPriority() {
    	if (owner.getActionsLeft()>1)
    		return DomCardName.Counting_House.getPlayPriority()-1;
    	return super.getPlayPriority();
    }
}