package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Set;

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
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      DomCard theChosenCard = null;
      for (DomCard theCard : owner.getCardsInHand()) {
          if (theChosenCard==null || theCard.countTypes()>theChosenCard.countTypes())
              theChosenCard=theCard;
      }
      if (DomEngine.haveToLog)
          DomEngine.addToLog(owner + " reveals " + theChosenCard);
      if (theChosenCard.getName()==DomCardName.Patron)
          theChosenCard.react();
      int theChoicesLeft = theChosenCard.countTypes();
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

    private void handleHuman() {
        Set<DomCardName> uniqueCards = owner.getUniqueCardNamesInHand();
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        theChooseFrom.clear();
        theChooseFrom.addAll(uniqueCards);
        DomCard theChosenCard = owner.getCardsFromHand(owner.getEngine().getGameFrame().askToSelectOneCard("Reveal a card for " + this.getName().toString(), theChooseFrom, "Mandatory!")).get(0);
        if (theChosenCard.getName()==DomCardName.Patron)
            theChosenCard.react();
        ArrayList<String> theOptions = new ArrayList<String>();
        if (theChosenCard.countTypes()==1) {
            theOptions.add("+Action");
            theOptions.add("+Buy");
            theOptions.add("+$3");
            theOptions.add("Gain Gold");
        }
        if (theChosenCard.countTypes()==2) {
            theOptions.add("+Action/+Buy");
            theOptions.add("+Action/+$3");
            theOptions.add("+Action/Gain Gold");
            theOptions.add("+Buy/+$3");
            theOptions.add("+Buy/Gain Gold");
            theOptions.add("+$3/Gain Gold");
        }
        if (theChosenCard.countTypes()==3) {
            theOptions.add("+Action/+Buy/+$3");
            theOptions.add("+Action/+Buy/Gain Gold");
            theOptions.add("+Action/+$3/Gain Gold");
            theOptions.add("+Buy/+$3/Gain Gold");
        }
        if (theChosenCard.countTypes()==4) {
            theOptions.add("+Action/+Buy/+$3/Gain Gold");
        }
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Select option", theOptions, "Mandatory!");
        if (theOptions.get(theChoice).contains("+Action"))
            owner.addActions(1);
        if (theOptions.get(theChoice).contains("+Buy"))
            owner.addAvailableBuys(1);
        if (theOptions.get(theChoice).contains("+$3"))
            owner.addAvailableCoins(3);
        if (theOptions.get(theChoice).contains("Gain Gold"))
            owner.gain(DomCardName.Gold);
    }

    private void chooseOption() {
        //standard handling
        if (!actionChosen && owner.getNextActionToPlay()!=null && owner.getActionsLeft()==0) {
            owner.addActions(1);
            actionChosen=true;
            return;
        }
        if (!buysChosen && owner.getBuysLeft()<=owner.getTotalPotentialCurrency().getCoins()/8.0) {
            owner.addAvailableBuys(1);
            buysChosen=true;
            return;
        }
        if (!goldChosen && owner.stillInEarlyGame() && owner.count(DomCardName.Gold)==0) {
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

    @Override
    public int getPlayPriority() {
        for (DomCard theCard:owner.getCardsInHand()) {
            if (theCard.countTypes()>3)
                return (theCard.getPlayPriority()-1);
        }
        for (DomCard theCard:owner.getCardsInHand()) {
            if (theCard.countTypes()>2)
                return (theCard.getPlayPriority()-1);
        }
        for (DomCard theCard:owner.getCardsInHand()) {
            if (theCard.countTypes()>1)
                return (theCard.getPlayPriority()-1);
        }
        return super.getPlayPriority();
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}