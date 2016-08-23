package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Fool$s_GoldCard extends DomCard {
    public Fool$s_GoldCard () {
      super( DomCardName.Fool$s_Gold);
    }

    public void play() {
      if (owner.getCardsFromPlay(DomCardName.Fool$s_Gold).size()>1){
    	  owner.addAvailableCoins(4);
      } else {
    	  owner.addAvailableCoins(1);
      }
    }
    //TODO reaction part is handled in ProvinceCard, maybe handle here
    
    @Override
    public double getPotentialCoinValue() {
    	//TODO needs checking (probably not correct)
    	if (!owner.getCardsInHand().contains(this) && !owner.getCardsInPlay().contains(this))
    		return owner.countInDeck(DomCardName.Fool$s_Gold);
    	double theNumberOfFG = owner.getCardsFromPlay(DomCardName.Fool$s_Gold).size()+owner.getCardsFromHand(DomCardName.Fool$s_Gold).size();
    	if (theNumberOfFG==0)
    		return 0;
    	return ( 1 + 4*(theNumberOfFG-1))/theNumberOfFG;
    }
}