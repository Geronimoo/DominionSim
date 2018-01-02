package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.enums.DomCardType;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class Black_MarketCard extends DomCard {
   protected static final Logger LOGGER = Logger.getLogger( Black_MarketCard.class );
   static {
       LOGGER.setLevel( DomEngine.LEVEL );
       LOGGER.removeAllAppenders();
       if (DomEngine.addAppender)
           LOGGER.addAppender(new ConsoleAppender(new SimpleLayout()) );
   }

    public Black_MarketCard () {
      super( DomCardName.Black_Market);
    }

    public void play() {
        owner.addAvailableCoins(2);
        ArrayList<DomCard> theRevealedCards = owner.getCurrentGame().revealFromBlackMarketDeck();
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + theRevealedCards );
        if (theRevealedCards.isEmpty() || owner.getDebt()>0)
        	return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman(theRevealedCards);
            return;
        }
        DomCard theCardToPlay;
        do {
            theCardToPlay = null;
            for (DomCard theCard1 : owner.getCardsInHand()) {
                if (theCard1.hasCardType(DomCardType.Treasure)) {
                     if (theCardToPlay == null || theCard1.getPlayPriority()<theCardToPlay.getPlayPriority()) {
                    	if (theCard1.wantsToBePlayed())
                          theCardToPlay = theCard1;
                     }
                }
            }
            if (theCardToPlay!=null) {
              owner.play(owner.removeCardFromHand(theCardToPlay));
            }
        } while (theCardToPlay!=null);

        if (DomEngine.haveToLog) {
          if (owner.getPreviousPlayedCardName()!=null) {
            DomEngine.addToLog( owner + " plays " + (owner.getSameCardCount()+1)+" "+ owner.getPreviousPlayedCardName().toHTML()
            		           + (owner.getSameCardCount()>0 ? "s" : ""));
            owner.setPreviousPlayedCardName(null);
            owner.setSameCardCount(0);
          }
          owner.showBuyStatus();
        }
        Collections.sort(theRevealedCards, SORT_FOR_TRASHING);
      DomCost theAvailableCurrency = owner.getTotalAvailableCurrency();
      DomCardName theDesiredCardName = owner.getDesiredCard(theAvailableCurrency, false);
      for (int i=theRevealedCards.size()-1 ; i>=0 ; i--) {
    	DomCard theCard = theRevealedCards.get(i);
        DomCost theCardCost = theCard.getName().getCost(owner.getCurrentGame());
    	if (theAvailableCurrency.compareTo(theCardCost)>=0 && theDesiredCardName==null) {
    		owner.buy(theRevealedCards.remove(i));
            break;
    	}
      }
      if (DomEngine.haveToLog) 
      	DomEngine.addToLog( owner + " returns " + theRevealedCards +" to the Black Market Deck");
      for (int i=theRevealedCards.size()-1 ; i>=0 ; i--) {
      	DomCard theCard = theRevealedCards.get(i);
      	owner.getCurrentGame().returnToBlackMarketDeck(theCard);
      }
    }

    private void handleHuman(ArrayList<DomCard> aRevealedCards) {
        ArrayList<DomCardName> theChooseFrom;
        DomCardName theCardToPlay;
        do {
            owner.setNeedsToUpdate();
            theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsFromHand(DomCardType.Treasure))
                theChooseFrom.add(theCard.getName());
            if (theChooseFrom.isEmpty())
                break;
            theCardToPlay = owner.getEngine().getGameFrame().askToSelectOneCard("Play Treasures", theChooseFrom, "Stop playing Treasures!");
            if (theCardToPlay != null) {
                owner.play(owner.removeCardFromHand(owner.getCardsFromHand(theCardToPlay).get(0)));
                if (owner.previousPlayedCardName != null) {
                    DomEngine.addToLog(owner + " plays " + (owner.sameCardCount + 1) + " " + owner.previousPlayedCardName.toHTML()
                            + (owner.sameCardCount > 0 ? "s" : ""));
                    owner.previousPlayedCardName = null;
                    owner.sameCardCount = 0;
                }
            }
        } while (theCardToPlay != null && !owner.getCardsFromHand(DomCardType.Treasure).isEmpty()) ;
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : aRevealedCards) {
            if (owner.getAvailableCoinsWithoutTokens()>=theCard.getCoinCost(owner.getCurrentGame()) && owner.availablePotions>=theCard.getPotionCost())
                theChooseFrom.add(theCard.getName());
        }
        if (!theChooseFrom.isEmpty()) {
            DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Buy a card", theChooseFrom, "Don't buy!");
            if (theChosenCard!=null) {
                for (int i = aRevealedCards.size() - 1; i >= 0; i--) {
                    DomCard theCard = aRevealedCards.get(i);
                    if (theChosenCard == theCard.getName()) {
                        owner.buy(aRevealedCards.remove(i));
                        break;
                    }
                }
            }
        }
        if (DomEngine.haveToLog)
            DomEngine.addToLog( owner + " returns " + aRevealedCards +" to the Black Market Deck");
        for (int i=aRevealedCards.size()-1 ; i>=0 ; i--) {
            DomCard theCard = aRevealedCards.get(i);
            owner.getCurrentGame().returnToBlackMarketDeck(theCard);
        }
    }
}