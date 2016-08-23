package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;
import java.util.Collections;

public class CatacombsCard extends DomCard {
    public CatacombsCard() {
      super( DomCardName.Catacombs);
    }

    public void play() {
      ArrayList<DomCard> theCards = owner.revealTopCards(3);
      int theTotal=0;
      for (DomCard card : theCards){
    	theTotal+=card.getDiscardPriority(owner.getActionsLeft());
    	if (card.getName()==DomCardName.Tunnel){
    		owner.discard(theCards);
            owner.drawCards(3);
    		return;
    	}
      }
      if (theTotal<45) {
        owner.discard(theCards);
        owner.drawCards(3);
      } else {
        for (DomCard theCard : theCards)
          owner.putInHand(theCard);
      }
    }

    @Override
    public int getPlayPriority() {
        return owner.getActionsLeft()>1 ? 6 : super.getPlayPriority();
    }

    @Override
    public void doWhenTrashed() {
        DomCardName theDesiredCard = owner.getDesiredCard(getCost(owner.getCurrentGame()).add(new DomCost(-1, 0)), false);
        if (theDesiredCard==null)
            theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner,null,getCost(owner.getCurrentGame()).add(new DomCost(-1, 0)));
        if (theDesiredCard!=null)
            owner.gain(theDesiredCard);
    }
}