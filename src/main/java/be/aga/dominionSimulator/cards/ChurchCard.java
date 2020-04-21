package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class ChurchCard extends DomCard {
    private ArrayList<DomCard> mySetAsideCards=new ArrayList<DomCard>();

    public ChurchCard() {
      super(DomCardName.Church);
    }

    public void play() {
      owner.addActions(1);
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      //remove unplayable terminals
      while (owner.getProbableActionsLeft()<0 && mySetAsideCards.size()<3) {
          ArrayList<DomCard> terminals = owner.getCardsFromHand(DomCardType.Terminal);
          Collections.sort(terminals,SORT_FOR_DISCARDING);
          setAside(owner.removeCardFromHand(terminals.get(terminals.size()-1)));
      }
      if (mySetAsideCards.size()==3 || owner.getCardsInHand().isEmpty())
          return;
      //remove draw card because it might draw cards dead
      if (owner.getProbableActionsLeft()==0 && !owner.getCardsFromHand(DomCardType.Card_Advantage).isEmpty() && owner.count(DomCardType.Village)>0) {
          ArrayList<DomCard> terminals = owner.getCardsFromHand(DomCardType.Terminal);
          Collections.sort(terminals,SORT_FOR_DISCARDING);
          setAside(owner.removeCardFromHand(terminals.get(terminals.size()-1)));
      }
      if (mySetAsideCards.size()==3 || owner.getCardsInHand().isEmpty())
          return;
      //set aside card to trash
      owner.getCardsInHand().sort(SORT_FOR_TRASHING);
      if (owner.getCardsInHand().get(0).getTrashPriority()< DomCardName.Copper.getTrashPriority() && !owner.getDeck().drawDeckHasJunkLeft())
          setAside(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
      if (mySetAsideCards.size()==3 || owner.getCardsInHand().isEmpty())
          return;
      //if we're going to buy a mediocre card (e.g. Silver), seed next hand
      if (!owner.stillInEarlyGame() && !owner.isGoingToBuyTopCardInBuyRules(owner.getTotalPotentialCurrency()) && !owner.getOpponents().isEmpty() && owner.countVictoryPoints()>=owner.getOpponents().get(0).countVictoryPoints() ) {
          while (!owner.getCardsFromHand(DomCardType.Treasure).isEmpty() && mySetAsideCards.size()<3) {
              ArrayList<DomCard> theTreasures = owner.getCardsFromHand(DomCardType.Treasure);
              Collections.sort(theTreasures,SORT_FOR_DISCARDING);
              setAside(owner.removeCardFromHand(theTreasures.get(theTreasures.size()-1)));
          }
      }
      if (mySetAsideCards.size()==3 || owner.getCardsInHand().isEmpty())
          return;
      //set aside Treasures we don't need this turn
      Collections.sort(owner.getCardsInHand(), DomCard.SORT_FOR_DISCARD_FROM_HAND);
      ArrayList<DomCard> theTreasures = owner.getCardsFromHand(DomCardType.Treasure);
      Collections.sort(theTreasures,SORT_FOR_DISCARDING);
      while (mySetAsideCards.size()<3 && !owner.getCardsInHand().isEmpty()) {
          if (theTreasures.isEmpty()) {
              checkForOtherJunk();
              return;
          }
          int i = theTreasures.size() - 1;
          while ((owner.removingReducesBuyingPower(theTreasures.get(i)) || theTreasures.get(i).getName() == DomCardName.Horn_of_Plenty) && i > 0)
              i--;
          if (owner.removingReducesBuyingPower(theTreasures.get(i)) || theTreasures.get(i).getName() == DomCardName.Horn_of_Plenty) {
              checkForOtherJunk();
              return;
          }
          setAside(owner.removeCardFromHand(theTreasures.remove(i)));
      }
    }

    private void setAside(DomCard domCard) {
        mySetAsideCards.add(domCard);
        if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has set aside " + mySetAsideCards);
    }

    private void handleHuman() {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();
        owner.getEngine().getGameFrame().askToSelectCards("Set aside cards", owner.getCardsInHand(), theChosenCards, 0);
        while (theChosenCards.size()>3)
            owner.getEngine().getGameFrame().askToSelectCards("Set aside cards", owner.getCardsInHand(), theChosenCards, 0);
        for (DomCard theCardName: theChosenCards) {
            for (DomCard theCard:owner.getCardsInHand()) {
                if (theCard.getName()==theCardName.getName()) {
                    mySetAsideCards.add(owner.removeCardFromHand(theCard));
                    break;
                }
            }
        }
        if (!mySetAsideCards.isEmpty())
            if (DomEngine.haveToLog) DomEngine.addToLog(owner + " has set aside " + mySetAsideCards);
    }

    private void checkForOtherJunk() {
        while (!owner.getCardsInHand().isEmpty() && mySetAsideCards.size()<3) {
            if (owner.getCardsInHand().get(0).getDiscardPriority(owner.getActionsLeft()) < DomCardName.Copper.getDiscardPriority(1))
                setAside(owner.getCardsInHand().remove(0));
            else
                return;
        }
    }

    public void resolveDuration() {
        owner.getCardsInHand().addAll(mySetAsideCards);
        if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " adds " + mySetAsideCards +" to hand");
        mySetAsideCards.clear();
        if (owner.isHumanOrPossessedByHuman()) {
            handleHumanForTrashing();
            return;
        }
        Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
        DomCard theCardToTrash=owner.getCardsInHand().get( 0 );
        if (theCardToTrash.getTrashPriority() < 16 && !owner.removingReducesBuyingPower( theCardToTrash )) {
            if (owner.count(DomCardName.Baron) > 0 && owner.count(DomCardName.Estate) < 3 && theCardToTrash.getName() == DomCardName.Estate) {
                if (!owner.getCardsFromHand(DomCardName.Copper).isEmpty() && !owner.removingReducesBuyingPower(owner.getCardsFromHand(DomCardName.Copper).get(0))) {
                    theCardToTrash = owner.getCardsFromHand(DomCardName.Copper).get(0);
                } else {
                    if (DomEngine.haveToLog) DomEngine.addToLog(owner + " trashes nothing");
                    return;
                }
            }
            owner.trash(owner.removeCardFromHand(theCardToTrash));
        }
    }

    private void handleHumanForTrashing() {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Don't trash");
        if (theChosenCard==null)
            return;
        owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
    }


    @Override
    public void cleanVariablesFromPreviousGames() {
        mySetAsideCards.clear();
    }
}