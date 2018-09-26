package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class FortuneCard extends DomCard {
    public FortuneCard() {
      super( DomCardName.Fortune);
    }
    
    @Override
    public void play() {
      owner.addAvailableBuys(1);
      if (owner.hasDoubledMoney())
          return;
      owner.addAvailableCoins(owner.getAvailableCoins()-owner.getCoffers());
      owner.setDoubledMoney(true);
    }

    @Override
    public double getPotentialCoinValue() {
      if (!owner.getCardsInHand().contains(this))
    	return super.getPotentialCoinValue();
        int theTotalCoins = owner.getAvailableCoins();
        for (int i = 0; i < owner.getCardsInHand().size(); i++) {
            DomCard theCardInHand = owner.getCardsInHand().get(i);
            if (theCardInHand.getName()!=DomCardName.Fortune)
              theTotalCoins += theCardInHand.getPotentialCoinValue();
        }
        theTotalCoins += owner.getCoffers();
        return theTotalCoins;
    }

    @Override
    public void doWhenGained() {
        final int numberOfGoldsToGain = owner.getCardsFromPlay(DomCardName.Gladiator).size();
        for (int i=0; i<numberOfGoldsToGain; i++) {
            owner.gain(DomCardName.Gold);
        }
    }
}