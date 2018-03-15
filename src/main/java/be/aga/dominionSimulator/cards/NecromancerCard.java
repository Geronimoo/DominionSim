package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class NecromancerCard extends DomCard {
    public NecromancerCard() {
      super( DomCardName.Necromancer );
    }

    public void play() {
        owner.setNeedsToUpdate();
        DomCard theChosenCard;
        boolean chooseNonTerminal=false;
        if (owner.getActionsLeft()==0 && !owner.getCardsFromHand(DomCardType.Action).isEmpty())
          chooseNonTerminal=true;
        theChosenCard = chooseCardToNecro(chooseNonTerminal);
        if (theChosenCard==null)
            return;
        if (DomEngine.haveToLog) DomEngine.addToLog( owner + " chooses to Necromance "+theChosenCard.getName().toHTML());
        owner.getCurrentGame().addFaceDownCard(theChosenCard);
        theChosenCard.setOwner(owner);
        owner.playThis(theChosenCard);
        theChosenCard.setOwner(null);
    }

    private DomCard chooseCardToNecro(boolean chooseNonTerminal) {
        if (owner.isHumanOrPossessedByHuman()) {
            return handleHuman();
        }
        ArrayList<DomCard> theActions = new ArrayList<DomCard>();
        for (DomCard theCard : owner.getCurrentGame().getTrashedCards()) {
            if (theCard.hasCardType(DomCardType.Action) && !theCard.hasCardType(DomCardType.Duration) && !owner.getCurrentGame().getFaceDownCardsInTrash().contains(theCard)){
               theActions.add(theCard);
               theCard.setOwner(owner);
            }
        }
        Collections.sort(theActions,SORT_FOR_DISCARDING_REVERSE);
        for (DomCard theCard:theActions) {
            if (theCard.wantsToBePlayed()) {
                theCard.setOwner(null);
                if (!theCard.hasCardType(DomCardType.Terminal) || !chooseNonTerminal)
                    return theCard;
            }
            theCard.setOwner(null);
        }
        for (DomCard theCard:theActions) {
            theCard.setOwner(owner);
            if (theCard.wantsToBePlayed()) {
                theCard.setOwner(null);
                return theCard;
            }
            theCard.setOwner(null);
        }
        return null;
    }

    private DomCard handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCurrentGame().getTrashedCards()) {
            if (theCard.hasCardType(DomCardType.Action) && !theCard.hasCardType(DomCardType.Duration) && !owner.getCurrentGame().getFaceDownCardsInTrash().contains(theCard)){
                theChooseFrom.add(theCard.getName());
            }
        }
        if (theChooseFrom.isEmpty())
            return null;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select card to Necromance", theChooseFrom, "Mandatory!");
        for (DomCard theCard : owner.getCurrentGame().getTrashedCards()) {
            if (theCard.getName()==theChosenCard && !owner.getCurrentGame().getFaceDownCardsInTrash().contains(theCard))
                return theCard;
        }
        return null;
    }
}