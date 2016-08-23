package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class BankCard extends DomCard {
    public BankCard () {
      super( DomCardName.Bank);
    }
    
    @Override
    public void play() {
      int theValue=0;
      for (DomCard theCard : owner.getCardsInPlay()) {
    	theValue+=theCard.hasCardType(DomCardType.Treasure) ? 1 : 0;
      }
      owner.addAvailableCoins(theValue);
    }
    @Override
    public double getPotentialCoinValue() {
      if (!owner.getCardsInHand().contains(this))
    	return super.getPotentialCoinValue();
      return owner.getCardsFromHand(DomCardType.Treasure).size()+owner.getCardsFromPlay(DomCardType.Treasure).size();
    }
}