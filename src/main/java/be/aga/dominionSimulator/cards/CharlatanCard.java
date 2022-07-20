package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class CharlatanCard extends DomCard {
    public CharlatanCard() {
      super( DomCardName.Charlatan);
    }

    public void play() {
      owner.addAvailableCoins(3);
      for (DomPlayer thePlayer : owner.getOpponents()) {
        if (!thePlayer.checkDefense()) {
          thePlayer.gain(DomCardName.Curse);
        }
      }
    }
    
    @Override
    public int getTrashPriority() {
      if (owner!=null) {
        if (owner.getCurrentGame().countInSupply( DomCardName.Curse )==0)
          return DomCardName.Moat.getTrashPriority(owner);
      }
      return super.getTrashPriority();
    }

    @Override
    public int getDiscardPriority( int aActionsLeft ) {
        if (owner!=null) {
            if (owner.getCurrentGame().countInSupply( DomCardName.Curse )==0)
                return DomCardName.Moat.getDiscardPriority(aActionsLeft);
        }
        return super.getDiscardPriority( aActionsLeft );
    }

    @Override
    public int getPlayPriority( ) {
      if (owner!=null) {
          if (owner.getCurrentGame().countInSupply( DomCardName.Curse )==0)
              return DomCardName.Moat.getPlayPriority();
      }
      return super.getPlayPriority();
    }
}