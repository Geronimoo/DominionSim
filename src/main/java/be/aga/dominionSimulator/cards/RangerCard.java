package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

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
      if (owner.getDeckAndDiscardSize()==0)
          return 100;
      return owner.getActionsAndVillagersLeft()>1 ? 8 : super.getPlayPriority();
    }

    @Override
    public int getDiscardPriority(int aActionsLeft) {
        if (aActionsLeft==0)
            return 1;
        if (owner.getDeckAndDiscardSize()==0)
            return 15;
        return super.getDiscardPriority(aActionsLeft);
    }

    @Override
    public boolean wantsToBePlayed() {
        if (!owner.isJourneyTokenFaceUp() && owner.getDeckAndDiscardSize()==0)
            return false;
//        if (!owner.isJourneyTokenFaceUp() && owner.actionsLeft<1)
//            return false;
        return true;
    }
}