package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class The_Flame$s_GiftCard extends DomCard {

    public The_Flame$s_GiftCard() {
      super( DomCardName.The_Flame$s_Gift);
    }
    
    @Override
    public void play() {
        if (owner.getCardsInHand().isEmpty())
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
        } else {
            Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
            DomCard theCardToTrash=owner.getCardsInHand().get( 0 );
            if (theCardToTrash.getTrashPriority() < 16 && !owner.removingReducesBuyingPower( theCardToTrash )) {
                if (owner.count(DomCardName.Baron)>0 && owner.count(DomCardName.Estate)<3 && theCardToTrash.getName()==DomCardName.Estate){
                    if (!owner.getCardsFromHand(DomCardName.Copper).isEmpty() && !owner.removingReducesBuyingPower(owner.getCardsFromHand(DomCardName.Copper).get(0))) {
                        theCardToTrash = owner.getCardsFromHand(DomCardName.Copper).get(0);
                    }else {
                        if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " trashes nothing");
                        return;
                    }
                }
                owner.trash(owner.removeCardFromHand( theCardToTrash));
            } else {
                if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " trashes nothing");
            }

        }
    }

    private void handleHuman() {
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Don't trash");
        if (theChosenCard!=null) {
            owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
        } else {
            if (DomEngine.haveToLog ) DomEngine.addToLog( owner + " trashes nothing");
        }
    }
}