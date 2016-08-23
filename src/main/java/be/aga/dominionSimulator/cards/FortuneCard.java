package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class FortuneCard extends DomCard {
    public FortuneCard() {
      super( DomCardName.Fortune);
    }
    
    @Override
    public void play() {
      owner.addAvailableBuys(1);
      if (owner.hasDoubledMoney())
          return;
      owner.addAvailableCoins(owner.getAvailableCoins()-owner.getCoinTokens());
      owner.setDoubledMoney(true);
    }

    @Override
    public double getPotentialCoinValue() {
      if (!owner.getCardsInHand().contains(this))
    	return super.getPotentialCoinValue();
      return owner.getCardsFromHand(DomCardType.Treasure).size()+owner.getCardsFromPlay(DomCardType.Treasure).size();
    }

    @Override
    public void doWhenGained() {
        for (DomCard theGladiator : owner.getCardsFromPlay(DomCardName.Gladiator)) {
            owner.gain(DomCardName.Gold);
        }
    }
}