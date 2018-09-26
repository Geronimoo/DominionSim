package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class MasqueradeCard extends DomCard {
    public MasqueradeCard () {
      super( DomCardName.Masquerade);
    }

    public void play() {
      owner.drawCards(2);
      if (!owner.getOpponents().isEmpty()) {
          if (!owner.getCardsInHand().isEmpty()) {
              owner.passCardToTheLeftForMasquerade(owner.chooseCardToPass(), owner);
          }else {
              ArrayList<DomPlayer> theOpponents = owner.getOpponents();
              int i = 0;
              while (i++!=theOpponents.size() && theOpponents.get(i-1).getCardsInHand().isEmpty() );
              if (i<=theOpponents.size()){
                  theOpponents.get(i-1).passCardToTheLeftForMasquerade(theOpponents.get(i-1).chooseCardToPass(), theOpponents.get(i-1));
              }
          }
      }
      Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
      if (owner.getCardsInHand().isEmpty())
    	  return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      DomCard theCardToTrash=owner.getCardsInHand().get( 0 );
      if (theCardToTrash.getTrashPriority() < 16 && !owner.removingReducesBuyingPower( theCardToTrash )) {
        if (owner.countInDeck(DomCardName.Baron)>0 && owner.countInDeck(DomCardName.Estate)<3 && theCardToTrash.getName()==DomCardName.Estate){
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