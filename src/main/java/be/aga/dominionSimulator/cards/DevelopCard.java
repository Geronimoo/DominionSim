package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

public class DevelopCard extends DomCard {

    public DevelopCard () {
      super( DomCardName.Develop);
    }

    public void play() {
        if (owner.getCardsInHand().isEmpty())
        	return;
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        Collections.sort( owner.getCardsInHand(), SORT_FOR_TRASHING);
        //find a card that will gain 2 cards
        for (DomCard theCard : owner.getCardsInHand()) {
          DomCardName theDesiredUpCard = owner.getDesiredCard(theCard.getName().getCost(owner.getCurrentGame()).add(new DomCost(1,0 ) ), true);
          if (theDesiredUpCard!=null) {
            DomCardName theDesiredDownCard = owner.getDesiredCard(theCard.getName().getCost(owner.getCurrentGame()).add(new DomCost(-1,0 ) ), true);
            if (theDesiredDownCard!=null) {
	           develop(owner.removeCardFromHand( theCard ));
	           return; 
            }
          }
        }
        //if no 2 useful cards can be gained, just trash the worst card
        develop(owner.removeCardFromHand( owner.getCardsInHand().get( 0 ) )); 
    }

    private void handleHuman() {
        ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsInHand()) {
            theChooseFrom.add(theCard.getName());
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!");
        owner.trash(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
        theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
            if (theCard.getCost(owner.getCurrentGame()).compareTo(theChosenCard.getCost(owner.getCurrentGame()).add(new DomCost(1,0)))==0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
            if (theCard.getCost(owner.getCurrentGame()).compareTo(theChosenCard.getCost(owner.getCurrentGame()).add(new DomCost(-1,0)))==0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        if (theChooseFrom.size()==1) {
            owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(theChooseFrom.get(0)));
        } else {
            DomCardName theFirstChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Gain a card for " + this.getName().toString(), theChooseFrom, "Mandatory!");
            owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(theFirstChosenCard));
            if (theFirstChosenCard.getCost(owner.getCurrentGame()).compareTo(theChosenCard.getCost(owner.getCurrentGame()))>0) {
                theChooseFrom = new ArrayList<DomCardName>();
                for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
                    if (theCard.getCost(owner.getCurrentGame()).compareTo(theChosenCard.getCost(owner.getCurrentGame()).add(new DomCost(-1,0)))==0 && owner.getCurrentGame().countInSupply(theCard)>0)
                        theChooseFrom.add(theCard);
                }
                if (theChooseFrom.isEmpty())
                    return;
                if (theChooseFrom.size()==1) {
                    owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(theChooseFrom.get(0)));
                } else {
                    DomCardName theLowerChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Gain a card for " + this.getName().toString(), theChooseFrom, "Mandatory!");
                    owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(theLowerChosenCard));
                }
            } else {
                theChooseFrom = new ArrayList<DomCardName>();
                for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
                    if (theCard.getCost(owner.getCurrentGame()).compareTo(theChosenCard.getCost(owner.getCurrentGame()).add(new DomCost(1, 0))) == 0 && owner.getCurrentGame().countInSupply(theCard) > 0)
                        theChooseFrom.add(theCard);
                }
                if (theChooseFrom.isEmpty())
                    return;
                if (theChooseFrom.size() == 1) {
                    owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(theChooseFrom.get(0)));
                } else {
                    DomCardName theHigherChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Gain a card for " + this.getName().toString(), theChooseFrom, "Mandatory!");
                    owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(theHigherChosenCard));
                }
            }
        }
    }

    private void develop(DomCard aCardToTrash) {
        DomCost theUpCost = aCardToTrash.getName().getCost(owner.getCurrentGame()).add(new DomCost(1,0 )) ;
        DomCost theDownCost = aCardToTrash.getName().getCost(owner.getCurrentGame()).add(new DomCost(-1,0 )) ;
        owner.trash( aCardToTrash );
        DomCardName theDownCardName = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theDownCost, true);
        if (theDownCardName!=null) {
          DomCard theDownCard = owner.getCurrentGame().takeFromSupply(theDownCardName);
          owner.gainOnTopOfDeck(theDownCard);
        }
        DomCardName theUpcardName = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, theUpCost, true);
        if (theUpcardName!=null) {
          DomCard theUpCard = owner.getCurrentGame().takeFromSupply(theUpcardName);
          owner.gainOnTopOfDeck(theUpCard);
        }
	}
	
    @Override
    public boolean wantsToBePlayed() {
        for (DomCard theCard : owner.getCardsInHand()) {
        	if (theCard==this)
        	  continue;
            if (theCard.getTrashPriority()<20)
              return true;
            DomCardName theDesiredUpCard = owner.getDesiredCard(theCard.getName().getCost(owner.getCurrentGame()).add(new DomCost(1,0 ) ), true);
            DomCardName theDesiredDownCard = owner.getDesiredCard(theCard.getName().getCost(owner.getCurrentGame()).add(new DomCost(-1,0 ) ), true);
            if (theDesiredUpCard!=null 
             && theDesiredDownCard!=null
             && theDesiredUpCard.getTrashPriority(owner)>=theCard.getTrashPriority())
            	return true;
        }
        return false;
   }

}