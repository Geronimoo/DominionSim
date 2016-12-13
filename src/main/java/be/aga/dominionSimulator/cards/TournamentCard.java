package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class TournamentCard extends DomCard {
    public TournamentCard () {
      super( DomCardName.Tournament);
    }

    public void play() {
      owner.addActions(1);
      if (!owner.getCardsFromHand(DomCardName.Province).isEmpty()){
    	  if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals a "+DomCardName.Province.toHTML()+" and may gain a Prize" );
    	  gainPrize();
    	  owner.discardFromHand(owner.getCardsFromHand(DomCardName.Province).get(0));
      }
      for (DomPlayer thePlayer : owner.getOpponents()){
        if (!thePlayer.getCardsFromHand(DomCardName.Province).isEmpty()){
            if (DomEngine.haveToLog) DomEngine.addToLog( thePlayer + " reveals a "+DomCardName.Province.toHTML()+" so "+ owner +" will not get $1 or draw a card" );
            return;
        }
      }
      owner.addAvailableCoins(1);
      owner.drawCards(1);
    }

	private void gainPrize() {
		for (DomBuyRule buyRule : owner.getBuyRules()){
			 if (buyRule.getCardToBuy()==DomCardName.Duchy
			 && owner.getCurrentGame().countInSupply(buyRule.getCardToBuy())>0
			 && buyRule.wantsToBuyOrGainNow(owner)){
				owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(buyRule.getCardToBuy()));
				return;
			}
		}
		for (DomBuyRule buyRule : owner.getPrizeBuyRules()){
			 if (owner.getCurrentGame().getBoard().isPrizeAvailable(buyRule.getCardToBuy())
			 && buyRule.wantsToBuyOrGainNow(owner)){
				owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(buyRule.getCardToBuy()));
				return;
			}
		}
	}

    @Override
    public int getPlayPriority() {
        if (owner.getCardsFromHand(DomCardName.Province).isEmpty() && !owner.getCardsFromHand(DomCardName.Hunting_Party).isEmpty())
            return owner.getCardsFromHand(DomCardName.Hunting_Party).get(0).getPlayPriority()+1;
        return super.getPlayPriority();
    }
}