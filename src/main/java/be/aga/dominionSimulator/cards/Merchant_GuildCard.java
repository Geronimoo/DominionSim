package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Merchant_GuildCard extends DomCard {

	public Merchant_GuildCard() {
      super( DomCardName.Merchant_Guild);
    }

    public void play() {        
      owner.addAvailableBuys(1);
      owner.addAvailableCoins(1);
   }
}