package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class LeprechaunCard extends DomCard {
    public LeprechaunCard() {
      super( DomCardName.Leprechaun);
    }

    public void play() {
      owner.gain(DomCardName.Gold);
      if (owner.getCardsInPlay().size()==7)
          owner.gain(DomCardName.Wish);
      else
          owner.receiveHex(null);
    }

    @Override
    public int getPlayPriority( ) {
      if (owner!=null && owner.getCardsInPlay().size()==6) {
          return 1;
      }
      return super.getPlayPriority( );
    }
}