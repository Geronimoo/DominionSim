package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.Collections;

public class StonemasonCard extends DomCard {
    public StonemasonCard() {
      super( DomCardName.Stonemason);
    }

    public void play() {
      if (!owner.getCardsInHand().isEmpty()) {
        Collections.sort( owner.getCardsInHand() , SORT_FOR_TRASHING);
        DomCard theCardToTrash = owner.getCardsInHand().get(0);
        for (DomCard card : owner.getCardsInHand()){
        	if ((card.getCoinCost(owner.getCurrentGame())==0 && card.getTrashPriority()<=DomCardName.Copper.getTrashPriority(owner))
        			|| card.getTrashPriority()==0) {
        		theCardToTrash = card;
        		break;
        	}
        }
        if (owner.getPlayStrategyFor(this)== DomPlayStrategy.combo) {
            if (!owner.getCardsFromHand(DomCardName.Peddler).isEmpty()) {
                theCardToTrash=owner.getCardsFromHand(DomCardName.Peddler).get(0);
            }
        }
        owner.trash(owner.removeCardFromHand( theCardToTrash ));
        int theCost = theCardToTrash.getCoinCost(owner.getCurrentGame());
        if (theCost>0) {
            for (int j=0;j<2;j++) {
                DomCardName theDesiredCard = owner.getDesiredCardWithRestriction(null, new DomCost(theCost - 1, theCardToTrash.getPotionCost()), false, DomCardName.Stonemason);
                if (theDesiredCard==null) {
                    theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null,new DomCost(theCost - 1, theCardToTrash.getPotionCost()));
                }
                if (theDesiredCard!=null)
                  owner.gain(theDesiredCard);
            }
        }
      }
    }

     @Override
    public boolean wantsToBePlayed() {
         if (owner.getPlayStrategyFor(this)== DomPlayStrategy.combo && !owner.getCardsFromHand(DomCardName.Peddler).isEmpty())
             return true;
         if (!owner.getCardsFromHand(DomCardName.Copper).isEmpty() || !owner.getCardsFromHand(DomCardName.Curse).isEmpty()) {
            return true;
        }
    	return false;
    }
}