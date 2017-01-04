package be.aga.dominionSimulator.cards;

import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class GovernorCard extends DomCard {
    public GovernorCard () {
      super( DomCardName.Governor);
    }

    public void play() {
      owner.addActions(1);
      if (owner.getPlayStrategyFor(this)==DomPlayStrategy.GoldEarlyTrashMid) {
    	playGoldEarlyTrashMid();
    	return;
      }
      if (!owner.getCardsFromHand(DomCardName.Minion).isEmpty()
       || !owner.getCardsFromHand(DomCardName.Tactician).isEmpty()
       || !owner.getCardsFromHand(DomCardName.Watchtower).isEmpty()
       || !owner.getCardsFromHand(DomCardName.Jack_of_all_Trades).isEmpty()
       || !owner.getCardsFromHand(DomCardName.Library).isEmpty()) {
    	  if (tryToRemodel())
    		  return;
		  if (!owner.getCardsFromHand(DomCardName.Minion).isEmpty() && owner.countInDeck(DomCardName.Minion)>5){
			  drawCards();
			  return;
		  }
		  gainGold();
		  return;
      }
      if (!owner.getCardsFromHand(DomCardName.Border_Village).isEmpty()
    	||!owner.getCardsFromHand(DomCardName.Farmland).isEmpty()){
    	  if (tryToRemodel())
    		  return;
      }
      if (owner.getDeckSize()>=3)
    	  drawCards();
      else
    	  gainGold();
    }

	private boolean tryToRemodel() {
		if (owner.getCardsInHand().isEmpty())
		  return false;
        Collections.sort( owner.getCardsInHand(), SORT_FOR_TRASHING);
        for (DomCard theCard : owner.getCardsInHand()) {
          DomCardName theDesiredCard = owner.getDesiredCard(theCard.getName().getCost(owner.getCurrentGame()).add(new DomCost(2,0 ) ), true);
          if (theDesiredCard!=null && theDesiredCard.getTrashPriority(owner)>=theCard.getTrashPriority() ) {
            owner.trash( owner.removeCardFromHand( theCard ) );
            owner.gain(theDesiredCard);
    	    for (DomPlayer domPlayer : owner.getOpponents()){
    		  upgradeOrRemodel(domPlayer,1);
    	    }
            return true;
          }
        }
		return false;
	}

	private void playGoldEarlyTrashMid() {
        if (owner.countInDeck(DomCardName.Governor)>3 && !owner.getCardsFromHand(DomCardName.Gold).isEmpty()) {
            remodelSomething();
            return;
        }
        if (owner.stillInEarlyGame() && owner.countInDeck(DomCardName.Gold)>0
                && owner.getDesiredCard(owner.getTotalPotentialCurrency(),false)!=DomCardName.Governor
              && owner.getDesiredCard(owner.getTotalPotentialCurrency(),false)!=DomCardName.Province) {
            drawCards();
            return;
        }
		if (owner.getCardsFromHand(DomCardName.Governor).size()>0
		 && !owner.getCardsFromHand(DomCardName.Gold).isEmpty()
         && !owner.stillInEarlyGame()) {
			remodelSomething();
			return;
		}
        if (bigTurnReady()){
            drawCards();
            return;
        }
 		if (owner.getCardsFromHand(DomCardName.Gold).isEmpty()
		|| (owner.getDesiredCard(owner.getTotalPotentialCurrency(),false)==DomCardName.Province && owner.getTotalPotentialCurrency().getCoins()<11)){ 
//		|| owner.getDesiredCard(owner.getTotalPotentialCurrency(),false)==DomCardName.Governor)  {
		  gainGold();
		  return;
		}
        if (owner.stillInEarlyGame()) {
            gainGold();
            return;
        }

		remodelSomething();
	}

	private boolean bigTurnReady() {
//		int governorsInHand = owner.getCardsFromHand(DomCardName.Governor).size();
//		int goldsInHand = owner.getCardsFromHand(DomCardName.Gold).size(); 
		int provincesLeftInSupply = owner.getCurrentGame().countInSupply(DomCardName.Province);
        return provincesLeftInSupply <= 4;
    }

	private void remodelSomething() {
		upgradeOrRemodel(owner,2);
	    for (DomPlayer domPlayer : owner.getOpponents()){
		  upgradeOrRemodel(domPlayer,1);
	    }
	}

	private void upgradeOrRemodel(DomPlayer player, int i) {
		if (player.getCardsInHand().isEmpty())
	      return;
		if (player==owner && owner.getPlayStrategyFor(this)==DomPlayStrategy.GoldEarlyTrashMid) {
			if (!owner.getCardsFromHand(DomCardName.Gold).isEmpty() && owner.getCurrentGame().countInSupply(DomCardName.Province)>0){
	            player.trash( player.removeCardFromHand( owner.getCardsFromHand(DomCardName.Gold).get(0) ) );
	            player.gain(DomCardName.Province);
	            return;
			}
		}
        Collections.sort( player.getCardsInHand(), SORT_FOR_TRASHING);
        for (DomCard theCard : player.getCardsInHand()) {
          DomCardName theDesiredCard = player.getDesiredCard(theCard.getName().getCost(player.getCurrentGame()).add(new DomCost(i,0 ) ), true);
          if (theDesiredCard!=null && theDesiredCard.getTrashPriority(player)>=theCard.getTrashPriority() ) {
            player.trash( player.removeCardFromHand( theCard ) );
            player.gain(theDesiredCard);
            return;
          }
        }
        //if nothing to gain, trash the worst card anyway (only if it's bad)
        if (player.removingReducesBuyingPower(player.getCardsInHand().get( 0 )) || player.getCardsInHand().get( 0 ).getTrashPriority()>=16)
        	return;
        DomCard theCardToTrash = player.removeCardFromHand( player.getCardsInHand().get( 0 ) );
		DomCost theCost = new DomCost( theCardToTrash.getCoinCost(player.getCurrentGame()) + 1, theCardToTrash.getPotionCost());
        player.trash( theCardToTrash );
        DomCardName theCardToGain = player.getCurrentGame().getBestCardInSupplyFor(player, null, theCost,true);
        if (theCardToGain!=null)
		  player.gain(theCardToGain);
	}

	private void gainGold() {
		if (owner.getCurrentGame().countInSupply(DomCardName.Gold)>0)
	      owner.gain(DomCardName.Gold);
	    for (DomPlayer domPlayer : owner.getOpponents()){
			if (domPlayer.getCurrentGame().countInSupply(DomCardName.Silver)>0)
			      domPlayer.gain(DomCardName.Silver);
	    }
	}

	private void drawCards() {
      owner.drawCards(3);
      for (DomPlayer domPlayer : owner.getOpponents())
    	domPlayer.drawCards(1);
	}
}