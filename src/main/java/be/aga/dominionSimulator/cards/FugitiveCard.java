package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class FugitiveCard extends TravellerCard {
    public FugitiveCard() {
      super( DomCardName.Fugitive);
      myUpgrade=DomCardName.Disciple;
    }

    public void play() {
      owner.addActions( 1 );
      owner.drawCards( 2 );
      owner.doForcedDiscard( 1, false );
    }
}