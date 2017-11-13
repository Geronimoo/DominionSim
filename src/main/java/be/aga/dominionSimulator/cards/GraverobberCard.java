package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class GraverobberCard extends DomCard {
    public GraverobberCard() {
      super( DomCardName.Graverobber);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        if (owner.getCardsFromHand(DomCardName.Graverobber).isEmpty() && owner.stillInEarlyGame() && findCardToGain())
            return;
        if (owner.getCardsFromHand(DomCardType.Action).isEmpty()) {
            findCardToGain();
            return;
        }
        Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
        if (owner.stillInEarlyGame()) {
            DomCard theCardToTrash = owner.getCardsFromHand(DomCardType.Action).get(0);
            DomCardName theCardToGain = owner.getDesiredCard(theCardToTrash.getCost(owner.getCurrentGame()).add(new DomCost(3, 0)), false);
            if (theCardToGain == null)
                theCardToGain = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theCardToTrash.getCost(owner.getCurrentGame()).add(new DomCost(3, 0)));
            if (theCardToGain != null) {
                owner.trash(owner.removeCardFromHand(theCardToTrash));
                if (theCardToGain != null)
                    owner.gain(theCardToGain);
                return;
            }
        }
        DomCard theCardToTrash = owner.getCardsFromHand(DomCardType.Action).get(owner.getCardsFromHand(DomCardType.Action).size() - 1);
        DomCardName theCardToGain = owner.getDesiredCard(theCardToTrash.getCost(owner.getCurrentGame()).add(new DomCost(3, 0)), false);
        if (theCardToGain == null)
            theCardToGain = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theCardToTrash.getCost(owner.getCurrentGame()).add(new DomCost(3, 0)));
        if (theCardToGain != null) {
            owner.trash(owner.removeCardFromHand(theCardToTrash));
            owner.gain(theCardToGain);
            return;
        }
        findCardToGain();
    }

    private void handleHuman() {
        ArrayList<String> theOptions = new ArrayList<String>();
        theOptions.add("Gain from trash");
        theOptions.add("Trash an Action");
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Select for Graverobber", theOptions, "Mandatory!");
        if (theChoice == 0) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCurrentGame().getTrashedCards()) {
                if (new DomCost(6, 0).compareTo(theCard.getCost(owner.getCurrentGame())) >= 0 && theCard.getCost(owner.getCurrentGame()).compareTo(new DomCost(3, 0)) >= 0)
                    theChooseFrom.add(theCard.getName());
            }
            if (theChooseFrom.isEmpty())
                return;
            DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Gain", theChooseFrom, "Mandatory!");
            owner.gainOnTopOfDeck(owner.getCurrentGame().removeFromTrash(theChosenCard));
        }
        if (theChoice==1) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsInHand()) {
                if (theCard.hasCardType(DomCardType.Action))
                  theChooseFrom.add(theCard.getName());
            }
            if (theChooseFrom.isEmpty())
                return;
            DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
            owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
            theChooseFrom = new ArrayList<DomCardName>();
            for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
                if (theChosenCard.getCost(owner.getCurrentGame()).add(new DomCost(3,0)).compareTo(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0)
                    theChooseFrom.add(theCard);
            }
            if (theChooseFrom.isEmpty())
                return;
            owner.gain(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain from "+this.getName().toString(), theChooseFrom, "Mandatory!"));
        }
    }

    private boolean findCardToGain() {
        ArrayList<DomCard> theCardsToConsider = new ArrayList<DomCard>();
        for (DomCard theCard:owner.getCurrentGame().getTrashedCards()) {
            if (theCard.getCoinCost(owner.getCurrentGame())>= 3 && theCard.getCoinCost(owner.getCurrentGame())<=6 && theCard.getPotionCost()==0 && theCard.getCost(owner.getCurrentGame()).getDebt()==0)
                theCardsToConsider.add(theCard);
        }
        if (theCardsToConsider.isEmpty())
            return false;
        Collections.sort(theCardsToConsider, SORT_FOR_TRASHING);
        owner.gainOnTopOfDeck(owner.getCurrentGame().getBoard().removeFromTrash(theCardsToConsider.get(theCardsToConsider.size() - 1)));
        return true;
    }

    @Override
    public int getPlayPriority() {
        if (owner.getCardsFromHand(DomCardType.Action).size()==2 || owner.getCardsFromHand(DomCardType.Terminal).size()==owner.getCardsFromHand(DomCardType.Action).size())
            if (!owner.stillInEarlyGame())
                return 0;
        return super.getPlayPriority();
    }
}