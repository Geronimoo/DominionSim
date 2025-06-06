package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Merchant_GuildCard extends DomCard {

	public Merchant_GuildCard() {
      super( DomCardName.Merchant_Guild);
    }

    public void play() {        
      owner.addAvailableBuys(1);
      owner.addAvailableCoins(1);
      owner.addMerchantGuildTrigger();
   }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}