package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class MargraveCard extends DomCard {
    public MargraveCard () {
      super( DomCardName.Margrave);
    }

    @Override
    public void play() {
      owner.addAvailableBuys( 1 );
      owner.drawCards( 3 );
      for (DomPlayer thePlayer : owner.getOpponents()) {
    	if (thePlayer.checkDefense())
    		continue;
        thePlayer.drawCards( 1 );
        thePlayer.doForcedDiscard(thePlayer.getCardsInHand().size()-3, false);
      }
    }
    
    public int getPlayPriority() {
      //put this higher up on the play priority if we have some actions to spare (enabling engine chains)
      return owner.getActionsLeft() > 1 && owner.getDeckSize()>0 ? 6 : super.getPlayPriority();  
    }
}