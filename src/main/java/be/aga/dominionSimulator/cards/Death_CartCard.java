package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Death_CartCard extends DomCard {
    public Death_CartCard () {
      super( DomCardName.Death_Cart);
    }

    public void play() {
      owner.addAvailableCoins(5);
      ArrayList<DomCard> theActions = owner.getCardsFromHand(DomCardType.Action);
      if (theActions.isEmpty()) {
        if (owner.getCardsInPlay().indexOf(this)!=-1)
    	  owner.trash(owner.removeCardFromPlay(this));
      } else {
          if (owner.isHumanOrPossessedByHuman()) {
              handleHuman();
              return;
          } else {
              Collections.sort(theActions, SORT_FOR_TRASHING);
              owner.trash(owner.removeCardFromHand(theActions.get(0)));
          }
      }
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            if (theCard.hasCardType(DomCardType.Action))
              theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card ?", theChooseFrom, "Don't trash");
        if (theChosenCard!=null) {
            owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
        } else {
            if (owner.getCardsInPlay().indexOf(this)!=-1)
                owner.trash(owner.removeCardFromPlay(this));
        }
    }

    @Override
    public void doWhenGained() {
        owner.gain(DomCardName.Ruins);
        owner.gain(DomCardName.Ruins);
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}