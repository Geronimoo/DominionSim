package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class AltarCard extends DomCard {
    public AltarCard() {
      super( DomCardName.Altar);
    }

    public void play() {
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      if (!owner.getCardsInHand().isEmpty()) {
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        owner.trash(owner.removeCardFromHand( owner.getCardsInHand().get( 0 ) ));
      }
      DomCardName theDesiredCard = owner.getDesiredCard(new DomCost( 5, 0), false);
      if (theDesiredCard==null) {
         theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(5, 0));
      }
      if (theDesiredCard!=null)
         owner.gain(theDesiredCard);
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
        owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (new DomCost(5,0).customCompare(theCard.getCost(owner.getCurrentGame()))>=0 )
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!")));
    }

    @Override
    public boolean wantsToBePlayed() {
        DomCardName theDesiredCard = owner.getDesiredCard(new DomCost( 5, 0), false);
        if (theDesiredCard==null)
            return false;
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        if (owner.getCardsInHand().size()==1)
            return true;
        if (theDesiredCard.getTrashPriority(owner)<=owner.getCardsInHand().get(0).getTrashPriority())
            return false;
        return true;
    }
}