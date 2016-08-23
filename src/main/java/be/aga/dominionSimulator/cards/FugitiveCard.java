package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class FugitiveCard extends DomCard {
    public FugitiveCard() {
      super( DomCardName.Fugitive);
    }

    public void play() {
      owner.addActions( 1 );
      owner.drawCards( 2 );
      owner.doForcedDiscard( 1, false );
    }

    @Override
    public void handleCleanUpPhase() {
        if (owner==null)
            return;
        if (owner.wants(DomCardName.Disciple)) {
            DomPlayer theOwner = owner;
            owner.returnToSupply(this);
            theOwner.gain(DomCardName.Disciple);
            return;
        }
        super.handleCleanUpPhase();
    }
}