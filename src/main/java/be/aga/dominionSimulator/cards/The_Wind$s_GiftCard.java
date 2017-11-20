package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class The_Wind$s_GiftCard extends DomCard {
    public The_Wind$s_GiftCard() {
      super( DomCardName.The_Wind$s_Gift);
    }

    public void play() {
      owner.drawCards( 2 );
      owner.doForcedDiscard( 2, false );
    }
}