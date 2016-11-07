package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Sea_HagCard extends DomCard {
    public Sea_HagCard () {
      super( DomCardName.Sea_Hag);
    }

    public void play() {
        for (DomPlayer thePlayer : owner.getOpponents()) {
          if (thePlayer.checkDefense()) 
        	continue;
          thePlayer.discardTopCardFromDeck();
          DomCard theCard = owner.getCurrentGame().takeFromSupply(  DomCardName.Curse );
          if (theCard!=null) {
            thePlayer.gainOnTopOfDeck(theCard);
          }
        }
    }
    
    @Override
    public int getTrashPriority() {
      if (owner!=null) {
        if (owner.getCurrentGame().countInSupply( DomCardName.Curse )==0)
          return 1;
      }
      return super.getTrashPriority();
    }
    
    @Override
    public int getDiscardPriority( int aActionsLeft ) {
        if (owner!=null) {
          if (owner.getCurrentGame().countInSupply( DomCardName.Curse )==0)
            return 1;
        }
        return super.getDiscardPriority( aActionsLeft );
    }
    
    @Override
    public int getPlayPriority( ) {
      if (owner!=null) {
          if (owner.getCurrentGame().countInSupply( DomCardName.Curse )==0)
              return 1000;
      }
      return super.getPlayPriority( );
    }
}