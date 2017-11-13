package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;

import java.util.ArrayList;
import java.util.Collections;

public class CrownCard extends MultiplicationCard {
    public CrownCard() {
      super( DomCardName.Crown);
    }

    @Override
    public void play() {
        if (owner.getPhase()== DomPhase.Action) {
            super.play();
            return;
        }
        ArrayList<DomCard> theTreasures = owner.getCardsFromHand(DomCardType.Treasure);
        if (theTreasures.isEmpty())
            return;
        Collections.sort(theTreasures, SORT_FOR_DISCARDING);
        DomCard theCardToPlayTwice=null;
        if (owner.isHumanOrPossessedByHuman()) {
            owner.setNeedsToUpdate();
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsFromHand(DomCardType.Treasure)) {
                theChooseFrom.add(theCard.getName());
            }
            DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Crown a card", theChooseFrom, "Don't Crown");
            if (theChosenCard != null) {
                theCardToPlayTwice = owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0));
            }
        } else {
            if (!owner.getCardsFromHand(DomCardName.Spoils).isEmpty())
                theCardToPlayTwice = owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Spoils).get(0));
            else if (!owner.getCardsFromHand(DomCardName.Capital).isEmpty())
                theCardToPlayTwice = owner.removeCardFromHand(owner.getCardsFromHand(DomCardName.Capital).get(0));
            else {
                int theChoice = 0;
                for (int i = theTreasures.size() - 1; i >= 0; i--) {
                    if (theTreasures.get(i).getName() == DomCardName.Fortune)
                        continue;
                    theChoice = i;
                    break;
                }
                theCardToPlayTwice = owner.removeCardFromHand(theTreasures.get(theChoice));
            }
        }
        if (theCardToPlayTwice==null)
            return;
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " chooses " + theCardToPlayTwice + " to Crown");
        owner.getCardsInPlay().add(theCardToPlayTwice);
        if (theCardToPlayTwice.getName()==DomCardName.Spoils) {
            theCardToPlayTwice.play();
            owner.addAvailableCoins(3);
            return;
        }
        theCardToPlayTwice.play();
        //fix for Horn of Plenty which might be trashed
        if (theCardToPlayTwice.owner==null) {
          theCardToPlayTwice.owner = owner;
          theCardToPlayTwice.play();
          theCardToPlayTwice.owner = null;
        }else {
          theCardToPlayTwice.play();
        }

    }

    @Override
    public int getPlayPriority() {
        if (owner.getPhase()==DomPhase.Buy)
            return 1;
        return super.getPlayPriority();
    }

    @Override
    public boolean wantsToBePlayed() {
        if (owner.getPhase()==DomPhase.Action && (owner.getCardsFromHand(DomCardType.Action).size()<2 || !owner.getCardsFromHand(DomCardName.Capital).isEmpty() || owner.getCardsFromHand(DomCardName.Crown).size()==owner.getCardsFromHand(DomCardType.Action).size()))
            return false;
        return true;
    }
}