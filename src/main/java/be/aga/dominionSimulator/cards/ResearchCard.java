package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class ResearchCard extends DomCard {
    private ArrayList<DomCard> myHavenedCards = new ArrayList<DomCard>();

    public ResearchCard() {
      super( DomCardName.Research);
    }

    public void play() {
      owner.addActions(1);
      if (owner.getCardsInHand().isEmpty())
    	  return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
      DomCard theCardToTrash = owner.getCardsInHand().get(0);
      for (DomCard theCard : owner.getCardsInHand()) {
          if (owner.getDeck().getDeckAndDiscardSize()>0 && getApprenticeValue(theCard) > 0 && enoughMoneyLeft(theCard) && areVPsNotInDanger(theCard) && theCard.getName()!=DomCardName.Market_Square){
              theCardToTrash=theCard;
              break;
          }
      }
      if (!owner.getCardsFromHand(DomCardName.Curse).isEmpty())
         theCardToTrash=owner.getCardsFromHand(DomCardName.Curse).get(0);
      if (owner.stillInEarlyGame() && !owner.getCardsFromHand(DomCardName.Estate).isEmpty())
         theCardToTrash=owner.getCardsFromHand(DomCardName.Estate).get(0);
      if (!owner.getCardsFromHand(DomCardName.Market_Square).isEmpty() && !owner.getCardsFromHand(DomCardName.Gold).isEmpty())
         theCardToTrash=owner.getCardsFromHand(DomCardName.Gold).get(0);
      owner.trash(owner.removeCardFromHand( theCardToTrash));
      havenAway(owner.getTopCards(getApprenticeValue(theCardToTrash)) );
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
        DomCard theCard = owner.getCardsFromHand(theChosenCard).get(0);
        owner.trash(owner.removeCardFromHand(theCard));
        havenAway(owner.getTopCards(getApprenticeValue(theCard)) );
    }

    private boolean areVPsNotInDanger(DomCard theCard) {
        if (theCard.getName()==DomCardName.Estate)
            return true;
        if (!theCard.hasCardType(DomCardType.Victory))
            return true;
        if (owner.countVictoryPoints()-theCard.getVictoryValue()<owner.countMaxOpponentsVictoryPoints())
            return false;
        return true;
    }

    private boolean enoughMoneyLeft(DomCard theCard) {
        if (theCard.getCoinValue()==0)
            return true;
        if (owner.getTotalMoneyInDeck()-theCard.getCoinValue()
                < (owner.getCurrentGame().countInSupply(DomCardName.Colony)>0 ? 11 : 8))
            //we don't want to destroy our buying power
            return false;
        return true;
    }

    private int getApprenticeValue(DomCard theCard) {
        return theCard.getCoinCost(owner.getCurrentGame())+theCard.getPotionCost()*2;
    }

    @Override
    public boolean wantsToBePlayed() {
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        if (owner.getDeck().getDeckAndDiscardSize()==0 && owner.getCardsInHand().get(0).getTrashPriority()>DomCardName.Copper.getTrashPriority())
            return false;
        return true;
    }

    private void havenAway(ArrayList<DomCard> cards) {
		myHavenedCards.addAll(cards);
        if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " puts " + myHavenedCards + " aside");
	}

    public void resolveDuration() {
      for (DomCard theCard : myHavenedCards) {
          owner.putInHand(theCard);
      }
      owner.showHand();
      myHavenedCards.clear();
    }

    public void cleanVariablesFromPreviousGames() {
        myHavenedCards.clear();
    }

    @Override
    public boolean durationFailed() {
        return myHavenedCards.isEmpty();
    }
}