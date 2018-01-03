package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class DoctorCard extends DomCard {
    public DoctorCard() {
      super( DomCardName.Doctor);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman()) {
          handleHumanPlay();
          return;
      }
      DomCardName theChoice=owner.getDeck().getMostLikelyCrappyCard();
      if (theChoice==null) {
          theChoice=DomCardName.Curse;
      }
      if (DomEngine.haveToLog) DomEngine.addToLog(owner + " names " + theChoice.toHTML());
      ArrayList<DomCard> theRevealedCards = owner.revealTopCards(3);
      ArrayList<DomCard> theCardsToPutBack = new ArrayList<DomCard>();
      for (DomCard theCard : theRevealedCards) {
          if (theCard.getName()==theChoice)
              owner.trash(theCard);
          else
              theCardsToPutBack.add(theCard);
      }
      Collections.sort(theCardsToPutBack, DomCard.SORT_FOR_DISCARDING);
      for (DomCard card:theCardsToPutBack) {
          owner.putOnTopOfDeck(card);
      }
    }

    private void handleHumanPlay() {
        ArrayList<DomCardName> theDeckCards = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getDeck().keySet()) {
            theDeckCards.add(theCard);
        }
        Collections.sort(theDeckCards);
        DomCardName theChoice = owner.getEngine().getGameFrame().askToSelectOneCard("Name a card", theDeckCards, "Name Ace of Spades");
        if (DomEngine.haveToLog) DomEngine.addToLog(owner + " names " + theChoice.toHTML());
        ArrayList<DomCard> theRevealedCards = owner.revealTopCards(3);
        ArrayList<DomCard> theCardsToPutBack = new ArrayList<DomCard>();
        for (DomCard theCard : theRevealedCards) {
            if (theCard.getName()==theChoice)
                owner.trash(theCard);
            else
                theCardsToPutBack.add(theCard);
        }
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>" , theCardsToPutBack, theChosenCards, theCardsToPutBack.size());
        for (int i=theChosenCards.size()-1;i>=0;i--) {
            for (DomCard theCard : theCardsToPutBack) {
                if (theChosenCards.get(i).getName()==theCard.getName()) {
                    owner.putOnTopOfDeck(theCard);
                    theCardsToPutBack.remove(theCard);
                    break;
                }
            }
        }
    }

    public void doWhenBought() {
        if (owner.isHumanOrPossessedByHuman()) {
            doHumanWhenBought();
            return;
        }
        for (int i=0;i<owner.getTotalAvailableCoins();i++) {
            ArrayList<DomCard> theRevealedCard = owner.revealTopCards(1);
            if (theRevealedCard.isEmpty())
                return;
            if (theRevealedCard.get(0).getTrashPriority()<=DomCardName.Copper.getTrashPriority(owner)) {
                owner.trash(theRevealedCard.get(0));
            } else {
                if (i==owner.getTotalAvailableCoins()-1 && theRevealedCard.get(0).getDiscardPriority(1)>20) {
                    owner.putOnTopOfDeck(theRevealedCard.get(0));
                } else {
                    owner.discard(theRevealedCard);
                }
            }
        }
        owner.setAvailableCoins(0);
        owner.spendCoinTokens(owner.getCoinTokens());
    }

    private void doHumanWhenBought() {
        ArrayList<String> theOptions = new ArrayList<String>();
        for (int i = 1; i <= owner.getAvailableCoinsWithoutTokens(); i++) {
            theOptions.add("Overpay $" + i);
        }
        int theOverpayAmount = owner.getEngine().getGameFrame().askToSelectOption("Overpay?", theOptions, "Don't overpay");
        if (theOverpayAmount==-1)
            return;
        owner.addAvailableCoins(-(theOverpayAmount+1));
        if (DomEngine.haveToLog)
            DomEngine.addToLog(owner + " overpays $" + (theOverpayAmount+1));
        for (int i=1;i<=theOverpayAmount+1;i++) {
            ArrayList<DomCard> theRevealedCard = owner.revealTopCards(1);
            if (theRevealedCard.isEmpty())
                return;
            theOptions = new ArrayList<String>();
            theOptions.add("<html>Trash " + theRevealedCard.get(0).getName().toHTML() + "</html>");
            theOptions.add("<html>Discard " + theRevealedCard.get(0).getName().toHTML() + "</html>");
            theOptions.add("<html>Put " + theRevealedCard.get(0).getName().toHTML() + " on top of deck</html>");
            int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Select for Doctor", theOptions, "Mandatory!");
            if (theChoice == 0)
                owner.trash(theRevealedCard.get(0));
            if (theChoice == 1)
                owner.discard(theRevealedCard.get(0));
            if (theChoice == 2)
                owner.gainOnTopOfDeck(theRevealedCard.get(0));
        }
    }
}