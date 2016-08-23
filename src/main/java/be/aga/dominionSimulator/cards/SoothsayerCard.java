package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class SoothsayerCard extends DomCard {
    public SoothsayerCard() {
      super( DomCardName.Soothsayer);
    }

    public void play() {
      for (DomPlayer thePlayer : owner.getOpponents()) {
        if (!thePlayer.checkDefense()) {
            if (owner.getCurrentGame().countInSupply( DomCardName.Curse )>0) {
                thePlayer.gain(DomCardName.Curse);
                thePlayer.drawCards(1);
            }
        }
      }
      owner.gain(DomCardName.Gold);
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
      return super.getPlayPriority( );
    }
}