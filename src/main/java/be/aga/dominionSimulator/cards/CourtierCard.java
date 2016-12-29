package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class CourtierCard extends DomCard {
    private boolean coinsChosen;
    private boolean actionChosen;
    private boolean buysChosen;
    private boolean goldChosen;

    public CourtierCard() {
      super( DomCardName.Courtier);
    }

    public void play() {
      if (owner.getCardsInHand().isEmpty())
          return;
      DomCard theChosenCard = null;
      for (DomCard theCard : owner.getCardsInHand()) {
          if (theChosenCard==null || theCard.getName().countLegalCardTypes()>theChosenCard.getName().countLegalCardTypes())
              theChosenCard=theCard;
      }
      if (DomEngine.haveToLog)
          DomEngine.addToLog(owner + " reveals " + theChosenCard);
      int theChoicesLeft = theChosenCard.getName().countLegalCardTypes();
      coinsChosen=false;
      actionChosen=false;
      buysChosen = false;
      goldChosen = false;

      while (theChoicesLeft>0 ) {
          if (coinsChosen && actionChosen && buysChosen && goldChosen)
              break;
          chooseOption();
          theChoicesLeft--;
      }
    }

    private void chooseOption() {
        //standard handling
        if (!actionChosen && owner.getNextActionToPlay()!=null && owner.getActionsLeft()==0) {
            owner.addActions(1);
            actionChosen=true;
            return;
        }
        if (!buysChosen && owner.getBuysLeft()<=1 && owner.getTotalPotentialCurrency().compareTo(new DomCost(11,0))>=0) {
            owner.addAvailableBuys(1);
            buysChosen=true;
            return;
        }
        if (!goldChosen && owner.stillInEarlyGame() && owner.countInDeck(DomCardName.Gold)==0) {
            owner.gain(DomCardName.Gold);
            goldChosen=true;
            return;
        }
        if (!coinsChosen && owner.addingThisIncreasesBuyingPower( new DomCost( 3,0 ))) {
            owner.addAvailableCoins(3);
            coinsChosen=true;
            return;
        }
        if (!goldChosen) {
            owner.gain(DomCardName.Gold);
            goldChosen=true;
            return;
        }
        if (!coinsChosen) {
            owner.addAvailableCoins(3);
            coinsChosen=true;
            return;
        }
        if (!actionChosen) {
            owner.addActions(1);
            actionChosen=true;
            return;
        }
        if (!buysChosen) {
            owner.addAvailableBuys(1);
            buysChosen=true;
            return;
        }
    }
}