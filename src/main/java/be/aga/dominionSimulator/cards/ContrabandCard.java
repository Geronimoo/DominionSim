package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class ContrabandCard extends DomCard {
    public ContrabandCard () {
      super( DomCardName.Contraband);
    }

    public void play() {
      owner.addAvailableCoins(3);
      owner.addAvailableBuys(1);
      DomCardName theChosenCard = null;
      if (!owner.getOpponents().isEmpty() && owner.getOpponents().get(0).isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      DomPlayer theRightOpponent = owner.getOpponents().get(owner.getOpponents().size()-1);
      ArrayList<DomCardName> cardsGainedLastTurn = theRightOpponent.getCardsGainedLastTurn();

        int theExpectedMoney=cardsGainedLastTurn.isEmpty()? 4 : cardsGainedLastTurn.get(0).getCoinCost(owner.getCurrentGame());
        for (;theChosenCard==null && theExpectedMoney>0;theExpectedMoney--) {
        //forbid buying a good card (add $3 to the average money in the deck to simulate a good turn)
        DomCost theExpectedCurrency = new DomCost(theExpectedMoney + 3, owner.countInDeck(DomCardName.Potion));
        theChosenCard = owner.getDesiredCard(theExpectedCurrency, false);
        //if multiple Contrabands played, make sure there are multiple forbidden cards to buy
        if (owner.getForbiddenCardsToBuy().contains(theChosenCard))
          theChosenCard=null;
      }
      if (theChosenCard==null)
          theChosenCard= DomCardName.Province;
      owner.addForbiddenCardToBuy(theChosenCard);
      if (DomEngine.haveToLog) 
          DomEngine.addToLog( owner + " can't buy " + theChosenCard.toHTML() +" this turn");
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
           theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Contraband " + this.getName().toString(), theChooseFrom, "Mandatory!");
        owner.addForbiddenCardToBuy(theChosenCard);
        if (DomEngine.haveToLog)
            DomEngine.addToLog( owner + " can't buy " + theChosenCard.toHTML() +" this turn");
    }
}