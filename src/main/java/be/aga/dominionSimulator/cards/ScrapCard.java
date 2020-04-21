package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class ScrapCard extends DomCard {
    public ScrapCard() {
      super( DomCardName.Scrap);
    }

    public void play() {
        if (owner.getCardsInHand().isEmpty())
            return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
      DomCard aCardToTrash = owner.removeCardFromHand(owner.getCardsInHand().get(0));
      owner.trash(aCardToTrash);
      int theChoicesCount=aCardToTrash.getCoinCost(owner.getCurrentGame());
      if (theChoicesCount==0)
          return;
      boolean coinsChosen=false;
      boolean actionChosen=false;
      boolean buysChosen = false;
      boolean silverChosen = false;
      boolean horseChosen = false;
      boolean drawChosen = false;

      if (owner.getNextActionToPlay()!=null)  {
          owner.addActions(1);
          actionChosen = true;
          theChoicesCount--;
      }
      if (theChoicesCount==0)
          return;
      if (owner.addingThisIncreasesBuyingPower(new DomCost(1,0))) {
          owner.addAvailableCoins(1);
          coinsChosen=true;
          theChoicesCount--;
      }
      if (theChoicesCount==0)
          return;
      if (owner.actionsLeft>0 && owner.getDeckAndDiscardSize()>0) {
          owner.drawCards(1);
          drawChosen = true;
          theChoicesCount--;
      }
      if (theChoicesCount==0)
          return;
      if (owner.getTotalPotentialCurrency().getCoins()>8 && owner.getBuysLeft()==1) {
          owner.addAvailableBuys(1);
          buysChosen=true;
          theChoicesCount--;
      }
      if (theChoicesCount==0)
          return;
      DomCard theHorse = owner.getCurrentGame().takeFromSupply(DomCardName.Horse);
      if (theHorse!=null) {
          owner.gain(theHorse);
          horseChosen=true;
          theChoicesCount--;
      }
      if (theChoicesCount==0)
          return;
      DomCard theSilver = owner.getCurrentGame().takeFromSupply(DomCardName.Silver);
      if (theSilver!=null) {
          owner.gain(theSilver);
          silverChosen=true;
          theChoicesCount--;
      }
      if (theChoicesCount==0)
          return;
      if (!drawChosen) {
          owner.drawCards(1);
          drawChosen=true;
          theChoicesCount--;
      }
      if (theChoicesCount==0)
          return;
      if (!buysChosen) {
          owner.addAvailableBuys(1);
          buysChosen=true;
          theChoicesCount--;
      }
      if (theChoicesCount==0)
          return;
      if (!coinsChosen) {
          owner.addAvailableCoins(1);
          coinsChosen=true;
          theChoicesCount--;
      }
      if (theChoicesCount==0)
          return;
      if (!actionChosen) {
          owner.addActions(1);
          actionChosen=true;
          theChoicesCount--;
      }
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
        owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));

        int theChoicesCount = theChosenCard.getCost(owner.getCurrentGame()).getCoins();
        boolean coinsChosen=false;
        boolean actionChosen=false;
        boolean buysChosen = false;
        boolean silverChosen = false;
        boolean horseChosen = false;
        boolean drawChosen = false;
        if (theChoicesCount>6)
            theChoicesCount=6;
        while (theChoicesCount > 0) {
            ArrayList<String> theOptions = new ArrayList<String>();
            theOptions.add("+Action");
            theOptions.add("+Coin");
            theOptions.add("+Buy");
            theOptions.add("+Card");
            theOptions.add("Gain a Horse");
            theOptions.add("Gain a Silver");
            int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Select option", theOptions, "Mandatory!");
            if (theChoice == 0 && !actionChosen) {
                actionChosen=true;
                theChoicesCount--;
            }
            if (theChoice == 1 && !coinsChosen) {
                coinsChosen = true;
                theChoicesCount--;
            }
            if (theChoice == 2 && !buysChosen) {
                buysChosen=true;
                theChoicesCount--;
            }
            if (theChoice == 3 && !drawChosen) {
                drawChosen=true;
                theChoicesCount--;
            }
            if (theChoice == 4 && !horseChosen) {
                horseChosen=true;
                theChoicesCount--;
            }
            if (theChoice == 5 && !silverChosen) {
                silverChosen=true;
                theChoicesCount--;
            }
        }
        if (actionChosen)
            owner.addActions(1);
        if (coinsChosen)
            owner.addAvailableCoins(1);
        if (buysChosen)
            owner.addAvailableBuys(1);
        if (horseChosen) {
            DomCard theHorse = owner.getCurrentGame().takeFromSupply(DomCardName.Horse);
            if (theHorse != null) {
                owner.gain(theHorse);
            }
        }
        if (silverChosen) {
            DomCard theSilver = owner.getCurrentGame().takeFromSupply(DomCardName.Silver);
            if (theSilver!=null) {
                owner.gain(theSilver);
            }
        }
        if (drawChosen)
            owner.drawCards(1);
    }

    @Override
    public boolean wantsToBePlayed() {
        if (owner.getCardsInHand().size()<=1)
            return false;
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        if (owner.getCardsInHand().get(0).getTrashPriority()<= DomCardName.Copper.getTrashPriority())
            return true;
        return false;
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }
}