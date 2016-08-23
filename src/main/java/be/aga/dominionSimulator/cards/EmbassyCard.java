package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class EmbassyCard extends DomCard {
    public EmbassyCard () {
      super( DomCardName.Embassy);
    }

    @Override
    public void play() {
      owner.drawCards( 5 );
      owner.doForcedDiscard(3, false);
    }
    
    @Override
    public int getPlayPriority() {
      //put this higher up on the play priority if we have some actions to spare (enabling engine chains)
      return owner.getActionsLeft() > 1 && owner.getDeckSize()>0 ? 6 : super.getPlayPriority();  
    }
    
    @Override
    public void doWhenGained() {
    	for (DomPlayer player : owner.getOpponents()){
    		if (owner.getCurrentGame().countInSupply(DomCardName.Silver)>0)
    			player.gain(DomCardName.Silver);
    	}
    }
}