package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;

public class GuideCard extends DomCard {
    public GuideCard() {
      super( DomCardName.Guide);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      if (owner.getCardsFromPlay(getName()).contains(this))
         owner.putOnTavernMat(owner.removeCardFromPlay(this));
    }

    public boolean wantsToBeCalled() {
        int theTotal = 0;
        for (DomCard card : owner.getCardsInHand()){
            theTotal+=card.getDiscardPriority(1);
            if (card.getName()==DomCardName.Tunnel){
                return true;
            }
        }
        return theTotal<80;
    }

    @Override
    public void doWhenCalled() {
        owner.discardHand();
        owner.drawCards(5);
    }
}