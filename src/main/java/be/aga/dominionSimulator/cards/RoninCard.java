package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class RoninCard extends DomCard {
    public RoninCard() {
      super( DomCardName.Ronin);
    }

    public void play() {
      owner.drawCards( 7 - owner.getCardsInHand().size() );
    }

    public boolean wantsToBePlayed() {
      if (owner.getCardsInHand().size()<=6 && owner.getDeckAndDiscardSize()>0)
    	  return super.wantsToBePlayed();
      return false;
    }

    @Override
    public int getPlayPriority() {
        if (owner.getActionsAndVillagersLeft()>1)
            return 20;
        if (owner.getActionsAndVillagersLeft()>2)
            return 35;

        return super.getPlayPriority();
    }
}