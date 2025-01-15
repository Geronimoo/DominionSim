package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Imperial_EnvoyCard extends DomCard {
    public Imperial_EnvoyCard() {
      super( DomCardName.Imperial_Envoy);
    }

    @Override
    public void play() {
      owner.addAvailableBuys( 1 );
      owner.drawCards( 5 );
      owner.addDebt(2);
    }
    
    public int getPlayPriority() {
      //put this higher up on the play priority if we have some actions to spare (enabling engine chains)
      return owner.getActionsAndVillagersLeft() > 1 && owner.getDeckAndDiscardSize()>0 ? 6 : 25;
    }

    @Override
    public boolean wantsToBePlayed() {
        return owner.getDeckAndDiscardSize()>0;
    }
}