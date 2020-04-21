package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;
import java.util.Collections;

public class StonemasonCard extends DomCard {
    public StonemasonCard() {
      super( DomCardName.Stonemason);
    }

    public void play() {
        if (owner.getCardsInHand().isEmpty())
          return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHumanPlay();
            return;
        }
        Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
        DomCard theCardToTrash = owner.getCardsInHand().get(0);
        for (DomCard card : owner.getCardsInHand()) {
            if ((card.getCoinCost(owner.getCurrentGame()) == 0 && card.getTrashPriority() <= DomCardName.Copper.getTrashPriority(owner))
                    || card.getTrashPriority() == 0) {
                theCardToTrash = card;
                break;
            }
        }
        if (owner.getPlayStrategyFor(this) == DomPlayStrategy.combo) {
            if (!owner.getCardsFromHand(DomCardName.Peddler).isEmpty()) {
                theCardToTrash = owner.getCardsFromHand(DomCardName.Peddler).get(0);
            }
        }
        owner.trash(owner.removeCardFromHand(theCardToTrash));
        int theCost = theCardToTrash.getCoinCost(owner.getCurrentGame());
        if (theCost > 0) {
            for (int j = 0; j < 2; j++) {
                DomCardName theDesiredCard = owner.getDesiredCardWithRestriction(null, new DomCost(theCost - 1, theCardToTrash.getPotionCost()), false, DomCardName.Stonemason);
                if (theDesiredCard == null) {
                    theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(theCost - 1, theCardToTrash.getPotionCost()));
                }
                if (theDesiredCard != null)
                    owner.gain(theDesiredCard);
            }
        }
    }

    private void handleHumanPlay() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
        if (theChosenCard != null) {
          owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
        }
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theChosenCard.getCost(owner.getCurrentGame()).customCompare(theCard.getCost(owner.getCurrentGame()))>0 && owner.getCurrentGame().countInSupply(theCard)>0 )
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theCardToGain = owner.getEngine().getGameFrame().askToSelectOneCard("Gain a card", theChooseFrom, "Mandatory!");
        owner.gain(theCardToGain);
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theChosenCard.getCost(owner.getCurrentGame()).customCompare(theCard.getCost(owner.getCurrentGame()))>0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        theCardToGain= owner.getEngine().getGameFrame().askToSelectOneCard("Gain a card", theChooseFrom, "Mandatory!");
        owner.gain(theCardToGain);
    }

    @Override
    public boolean wantsToBePlayed() {
         if (owner.getPlayStrategyFor(this)== DomPlayStrategy.combo && !owner.getCardsFromHand(DomCardName.Peddler).isEmpty())
             return true;
         if (!owner.getCardsFromHand(DomCardName.Copper).isEmpty() || !owner.getCardsFromHand(DomCardName.Curse).isEmpty()) {
            return true;
        }
    	return false;
    }

    public void doWhenBought() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHumanWhenBought();
            return;
        }
        DomCardName theDesiredCard = owner.getDesiredCardWithRestriction(DomCardType.Action, owner.getTotalAvailableCurrency(), false, DomCardName.Stonemason);
        if (theDesiredCard != null) {
            owner.addAvailableCoins(-theDesiredCard.getCoinCost(owner));
            owner.availablePotions -= theDesiredCard.getPotionCost();
            if (DomEngine.haveToLog)
                DomEngine.addToLog(owner + " overpays " + theDesiredCard.getCost(owner.getCurrentGame()));
            owner.gain(theDesiredCard);
            theDesiredCard = owner.getDesiredCardWithRestriction(DomCardType.Action, theDesiredCard.getCost(owner.getCurrentGame()), true, DomCardName.Stonemason);
            if (theDesiredCard != null)
                owner.gain(theDesiredCard);
        }
    }

    private void handleHumanWhenBought() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (owner.getTotalAvailableCurrency().customCompare(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0 && theCard.hasCardType(DomCardType.Action))
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Overpay?", theChooseFrom, "Don't overpay");
        if (theChosenCard==null)
            return;
        owner.gain(theChosenCard);
        owner.addAvailableCoins(-theChosenCard.getCoinCost(owner));
        owner.availablePotions-=theChosenCard.getPotionCost();
        if (DomEngine.haveToLog)
            DomEngine.addToLog(owner + " overpays " + theChosenCard.getCost(owner.getCurrentGame()));
        owner.setNeedsToUpdateGUI();
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theChosenCard.getCost(owner.getCurrentGame()).customCompare(theCard.getCost(owner.getCurrentGame()))==0 && owner.getCurrentGame().countInSupply(theCard)>0 && theCard.hasCardType(DomCardType.Action))
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gain(owner.getEngine().getGameFrame().askToSelectOneCard("Gain a second card", theChooseFrom, "Mandatory!"));
    }
}