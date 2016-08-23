package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class WeddingCard extends DomCard {
    public WeddingCard() {
      super( DomCardName.Wedding);
    }

    public void play() {
      owner.addVP(1);
      DomCard theGold = owner.getCurrentGame().takeFromSupply( DomCardName.Gold );
      if (theGold!=null) {
        owner.gain( theGold );
      }  
    }
}