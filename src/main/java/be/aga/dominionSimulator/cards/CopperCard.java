package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class CopperCard extends DomCard {

    public CopperCard () {
      super( DomCardName.Copper);
    }
    @Override
    public int getCoinValue() {
        return owner.getCoppersmithPlayedCount() +1;
    }
    @Override
    public boolean wantsToBePlayed() {
        if (owner==null) {
            System.out.println("Copper owner = null: "+ DomEngine.currentPlayer.getDeck());
            System.out.println("opp deck: "+ DomEngine.currentPlayer.getOpponents().get(0).getDeck());
        }
        if (owner.isTrashingTokenSet()
          && countCrapCards()==0
          && owner.getCardsFromHand(DomCardName.Copper).size()==1
          && owner.getDesiredCard(owner.getTotalPotentialCurrency().add(new DomCost(-1,0)),false)==owner.getTrashingTokenOn())
            return false;
        if (owner.countInDeck(DomCardName.Exorcist)>0
                && !owner.getCardsFromHand(DomCardName.Exorcist).isEmpty()
                && owner.getCardsFromHand(DomCardName.Copper).size()==1
                && countCrapCards()==0
                && owner.getDesiredCard(owner.getTotalPotentialCurrency().add(new DomCost(-1,0)),false)==owner.getDesiredCard(owner.getTotalPotentialCurrency(),false))
            return false;
        if (owner.countInDeck(DomCardName.Bat)>0
                && !owner.getCardsFromHand(DomCardName.Bat).isEmpty()
                && owner.getCardsFromHand(DomCardName.Copper).size()==1
                && countCrapCards()<2
                && owner.getDesiredCard(owner.getTotalPotentialCurrency().add(new DomCost(-1,0)),false)==owner.getDesiredCard(owner.getTotalPotentialCurrency(),false))
            return false;
    	if (handlePossibleGrandMarketBuy())
    	  return false;
    	if (owner.getCurrentGame().countInSupply(DomCardName.Ducat)>0
            && owner.getCardsFromHand(DomCardName.Copper).size()==1
    	    && owner.getDesiredCard(owner.getTotalPotentialCurrency(),false)==DomCardName.Ducat
            && owner.getTotalPotentialCurrency().getCoins()>2)
    	    return false;
    	return true;
    }

    private int countCrapCards() {
        int counter = 0;
        for (DomCard theCard : owner.getCardsInHand()) {
            if (theCard.getTrashPriority() < DomCardName.Copper.getTrashPriority())
                counter++;
        }
        return counter;
    }

    private boolean handlePossibleGrandMarketBuy() {
		//if we've already played a copper or there is no Grand Market in the supply, we can play the Copper
		if (owner.getCurrentGame().countInSupply(DomCardName.Grand_Market)==0
    	  || !owner.getCardsFromPlay(DomCardName.Copper).isEmpty())
    		return false;
		//if we are going to buy a better card then Grand Market this turn (Colony perhaps) just play the Copper
    	DomCardName theDesiredCard = owner.getDesiredCard(owner.getTotalPotentialCurrency(), false);
    	if (theDesiredCard!=null && theDesiredCard.getTrashPriority(owner)>
    	  DomCardName.Grand_Market.getTrashPriority(owner))
    		return false;
    	//finally if we can buy a Grand Market with the current available money, don't play the Copper 
    	ArrayList<DomCard> theCoppersInHand = owner.getCardsFromHand(DomCardName.Copper);
    	DomCost theCurrencyWithoutCoppers = owner.getTotalPotentialCurrency().add(new DomCost(-theCoppersInHand.size()*getCoinValue(),0));
    	if (owner.getDesiredCard(theCurrencyWithoutCoppers, false) == DomCardName.Grand_Market)
    		return true;
    	//just play that Copper
    	return false;
	}

    @Override
    public int getPlayPriority() {
        if (owner.countInDeck(DomCardName.Magic_Lamp)>0
                && !owner.getCardsFromHand(DomCardName.Magic_Lamp).isEmpty()
                && owner.getCardsFromPlay(DomCardName.Copper).isEmpty())
            return 5;
        return super.getPlayPriority();
    }
}