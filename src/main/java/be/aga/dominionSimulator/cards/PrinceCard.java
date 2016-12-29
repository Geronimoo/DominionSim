package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class PrinceCard extends DomCard {
    public PrinceCard() {
      super( DomCardName.Prince);
    }

    public void play() {
        if (owner==null)
            return;
        Collections.sort(owner.getCardsInHand(),SORT_FOR_TRASHING);
        for (int i=owner.getCardsInHand().size()-1;i>0;i--) {
            if (owner.getCardsInHand().get(i).hasCardType(DomCardType.Action)
                    && owner.getCardsInHand().get(i).getCost(owner.getCurrentGame()).compareTo(new DomCost(4,0))<=0) {
                if (owner.getCardsFromPlay(getName()).contains(this))
                   owner.removeCardFromPlay(this);
                if (DomEngine.haveToLog) DomEngine.addToLog( owner + " sets aside " + owner.getCardsInHand().get(i) + " with " + this);
                owner.setAsideForPrince(owner.removeCardFromHand(owner.getCardsInHand().get(i)));
            }
        }
    }

    @Override
    public boolean wantsToBePlayed() {
        for (int i = owner.getCardsInHand().size() - 1; i > 0; i--) {
            if (owner.getCardsInHand().get(i).hasCardType(DomCardType.Action)
                    && owner.getCardsInHand().get(i).getCost(owner.getCurrentGame()).compareTo(new DomCost(4, 0)) <= 0
                    && owner.getCardsInHand().get(i) != this) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getPlayPriority() {
        ArrayList<DomCard> theCards = new ArrayList<DomCard>();
        theCards.addAll(owner.getCardsInHand());
        int theCount=0;
        DomCard theCardToPrince=null;
        for (DomCard theCard : theCards) {
            if (theCard.hasCardType(DomCardType.Action)
                    && theCard.getCost(owner.getCurrentGame()).compareTo(new DomCost(4, 0)) <= 0) {
                theCount++;
                theCardToPrince = theCard;
            }
        }
        if (theCount==1) {
            return (theCardToPrince.getPlayPriority()-1);
        }
        return super.getPlayPriority();
    }
}