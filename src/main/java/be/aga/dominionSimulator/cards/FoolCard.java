package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class FoolCard extends DomCard {
    public FoolCard() {
      super( DomCardName.Fool);
    }

    public void play() {
      if (owner.isLostInTheWoods())
          return;
      owner.setLostInTheWoods(true);
      for (DomPlayer theOpp:owner.getOpponents())
          theOpp.setLostInTheWoods(false);
      owner.receiveBoon(null);
      owner.receiveBoon(null);
      owner.receiveBoon(null);
    }

    @Override
    public int getDiscardPriority(int aActionsLeft) {
        if (owner.isLostInTheWoods())
            return 1;
        return super.getDiscardPriority(aActionsLeft);
    }

    @Override
    public int getPlayPriority() {
        if (owner.isLostInTheWoods())
            return 1000;
        return super.getPlayPriority();
    }
}