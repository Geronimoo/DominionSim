package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class WitchCard extends DomCard {
    public WitchCard () {
      super( DomCardName.Witch);
    }

    public void play() {
      owner.drawCards(2);
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