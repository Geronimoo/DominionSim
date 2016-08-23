package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class SacrificeCard extends DomCard {
    public SacrificeCard() {
      super( DomCardName.Sacrifice);
    }

    public void play() {
      DomCard theCardToTrash = null;
      if (owner.getCardsInHand().isEmpty())
          return;
      theCardToTrash = findCardToTrash();
      if (theCardToTrash==null) {
        //this is needed when card is played with Throne Room effect
        Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
        theCardToTrash=owner.getCardsInHand().get(0);
      }
      owner.trash(owner.removeCardFromHand( theCardToTrash ));
      if (theCardToTrash.hasCardType(DomCardType.Action)) {
        owner.addActions(2);
        owner.drawCards(2);
      }
      if (theCardToTrash.hasCardType(DomCardType.Treasure)) {
        owner.addAvailableCoins(2);
      }
      if (theCardToTrash.hasCardType(DomCardType.Victory)) {
        owner.addVP(2);
      }
    }

	private DomCard findCardToTrash() {
      Collections.sort( owner.getCardsInHand(), SORT_FOR_TRASHING);
      DomCard theCardToTrash = owner.getCardsInHand().get( 0 );
      if (theCardToTrash==this && owner.getCardsInHand().size()>1)
    	  theCardToTrash = owner.getCardsInHand().get( 1 );
      return theCardToTrash;
	}

    public boolean wantsToBePlayed() {
        for (DomCard theCard : owner.getCardsInHand()) {
            if (theCard!=this && theCard.getTrashPriority()<16 )
                return true;
        }
        return false;
    }
}