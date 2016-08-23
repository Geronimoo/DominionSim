package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class RangerCard extends DomCard {
    public RangerCard() {
      super( DomCardName.Ranger);
    }

    public void play() {
        owner.addAvailableBuys(1);
        owner.flipJourneyToken();
        if (owner.isJourneyTokenFaceUp())
            owner.drawCards(5);
    }

    @Override
    public int getPlayPriority() {
      if (owner.getDeckSize()==0)
          return 100;
      return owner.getActionsLeft()>1 ? 8 : super.getPlayPriority();
    }

    @Override
    public int getDiscardPriority(int aActionsLeft) {
        if (aActionsLeft==0)
            return 1;
        if (owner.getDeckSize()==0)
            return 15;
        return super.getDiscardPriority(aActionsLeft);
    }
}