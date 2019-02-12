package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class TournamentCard extends DomCard {
    public TournamentCard () {
      super( DomCardName.Tournament);
    }

    public void play() {
      owner.addActions(1);
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
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

    private void handleHuman() {
        if (!owner.getCardsFromHand(DomCardName.Province).isEmpty()) {
            if (owner.getEngine().getGameFrame().askPlayer("<html>Reveal " + DomCardName.Province.toHTML() + "?</html>", "Resolving " + this.getName().toString())) {
                owner.discardFromHand(DomCardName.Province);
                ArrayList<DomCardName> thePrizes = new ArrayList<DomCardName>();
                for (DomCard theCard : owner.getCurrentGame().getBoard().getPrizes()) {
                    thePrizes.add(theCard.getName());
                }
                if (owner.getCurrentGame().countInSupply(DomCardName.Duchy) > 0)
                    thePrizes.add(DomCardName.Duchy);
                if (!thePrizes.isEmpty()) {
                    DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Gain a Prize", thePrizes, "Mandatory!");
                    owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(theChosenCard));
                }
            }
        }
        for (DomPlayer thePlayer : owner.getOpponents()){
            if (!thePlayer.getCardsFromHand(DomCardName.Province).isEmpty()){
                if (thePlayer.isHuman()) {
                    thePlayer.setNeedsToUpdateGUI();
                    if (owner.getEngine().getGameFrame().askPlayer("<html>Reveal " + DomCardName.Province.toHTML() +"?</html>", "Resolving " + this.getName().toString())){
                        if (DomEngine.haveToLog) DomEngine.addToLog( thePlayer + " reveals a "+DomCardName.Province.toHTML()+" so "+ owner +" will not get $1 or draw a card" );
                        return;
                    }
                } else {
                    if (DomEngine.haveToLog)
                        DomEngine.addToLog(thePlayer + " reveals a " + DomCardName.Province.toHTML() + " so " + owner + " will not get $1 or draw a card");
                    return;
                }
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

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}