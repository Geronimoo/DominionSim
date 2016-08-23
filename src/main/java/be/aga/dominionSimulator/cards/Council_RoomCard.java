package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Council_RoomCard extends DomCard {
    public Council_RoomCard () {
      super( DomCardName.Council_Room);
    }

    @Override
    public void play() {
      owner.addAvailableBuys( 1 );
      owner.drawCards( 4 );
      for (DomPlayer thePlayer : owner.getOpponents()) {
        thePlayer.drawCards( 1 );
      }
    }
    
    public int getPlayPriority() {
      //put this higher up on the play priority if we have some actions to spare (enabling engine chains)
      return owner.getActionsLeft() > 1 && owner.getDeckSize()>0 ? 6 : 25;  
    }
}