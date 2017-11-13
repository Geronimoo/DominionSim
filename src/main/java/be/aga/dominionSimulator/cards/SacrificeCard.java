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
      if (owner.isHumanOrPossessedByHuman()) {
          ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
          for (DomCard theCard : owner.getCardsInHand()) {
              theChooseFrom.add(theCard.getName());
          }
          theCardToTrash = owner.getCardsFromHand(owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!")).get(0);
      } else {
          theCardToTrash = findCardToTrash();
          if (theCardToTrash == null) {
              //this is needed when card is played with Throne Room effect
              Collections.sort(owner.getCardsInHand(), SORT_FOR_TRASHING);
              theCardToTrash = owner.getCardsInHand().get(0);
          }
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
      if (!theCardToTrash.hasCardType(DomCardType.Action) && owner.getActionsLeft()==0 && !owner.getCardsFromHand(DomCardType.Action).isEmpty())
          theCardToTrash=owner.getCardsFromHand(DomCardType.Action).get(0);
      return theCardToTrash;
	}

    @Override
    public int getPlayPriority() {
        if (owner.getActionsLeft()==1 && owner.getCardsFromHand(DomCardType.Action).size()>1) {
            for (DomCard theCard : owner.getCardsFromHand(DomCardType.Action)){
                if (theCard!=this && theCard.getTrashPriority()<=getTrashPriority())
                    return 15;
            }
        }
        return super.getPlayPriority();
    }

    public boolean wantsToBePlayed() {
        for (DomCard theCard : owner.getCardsInHand()) {
            if (theCard==this)
                continue;
            if (theCard.getName()==DomCardName.Sacrifice)
                return true;
            if (theCard!=this && theCard.getTrashPriority()<16 )
                return true;
        }
        return false;
    }

    @Override
    public boolean wantsToBeMultiplied() {
        int count = 0;
        for (DomCard theCard : owner.getCardsInHand()) {
            if (theCard!=this && theCard.getTrashPriority()<16 )
                count++;
        }
        if (owner.countInDeck(DomCardName.King$s_Court)>0)
          return count>2;
        return count>1;
    }
}