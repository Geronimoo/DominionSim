package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class TraderCard extends DomCard {
    private DomCard lastTradedCard = null;

    public TraderCard () {
      super( DomCardName.Trader);
    }

    public void play() {
      if (!owner.getCardsInHand().isEmpty()) {
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        DomCard theCardToTrash = owner.getCardsInHand().get(0);
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsInHand()) {
                theChooseFrom.add(theCard.getName());
            }
            theCardToTrash = owner.getCardsFromHand(owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Mandatory!")).get(0);
        } else {
            for (DomCard card : owner.getCardsInHand()) {
                if ((card.getCoinCost(owner.getCurrentGame()) > 0 && card.getTrashPriority() <= 19)
                        || card.getTrashPriority() == 0) {
                    theCardToTrash = card;
                    break;
                }
            }
        }
        owner.trash(owner.removeCardFromHand( theCardToTrash ));
        for (int i=0;i<theCardToTrash.getCoinCost(owner.getCurrentGame());i++) {
            DomCard theSilver = owner.getCurrentGame().takeFromSupply( DomCardName.Silver);
            if (theSilver!=null) {
              owner.gain(theSilver);
            }
        }
      }
    }
    
    public boolean react(DomCard aCard) {
      if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + this );
      lastTradedCard = aCard;
      owner.getCurrentGame().returnToSupply( aCard );
      owner.gain(DomCardName.Silver);
      return true;
    }

     public boolean wantsToReact(DomCard aCard) {
         if (owner.isHumanOrPossessedByHuman()) {
             owner.setNeedsToUpdate();
             return owner.getEngine().getGameFrame().askPlayer("<html>React with " + this.getName().toHTML() +" ?</html>", "Resolving " + this.getName().toString());
         } else {
             //TODO this way of handling Trader looks a bit dirty (= Watchtower)
             if (aCard.getName() == DomCardName.Silver || owner.getCurrentGame().countInSupply(DomCardName.Silver) == 0)
                 return false;
             if (aCard.getName().getTrashPriority(owner) < 16) {
                 return true;
             } else {
                 return false;
             }
         }
     }
     @Override
    public boolean wantsToBePlayed() {
    	if (owner.getDesiredCard(owner.getTotalPotentialCurrency(), false)==DomCardName.Cache)
    		return false;
    	if (owner.getCardsInHand().get(0).getName()==DomCardName.Silver && owner.getPlayStrategyFor(owner.getCardsInHand().get(0))== DomPlayStrategy.trashWhenObsolete)
    	    return false;
    	return true;
    }
}