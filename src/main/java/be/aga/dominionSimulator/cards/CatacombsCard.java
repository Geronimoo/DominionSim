package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class CatacombsCard extends DomCard {
    public CatacombsCard() {
      super( DomCardName.Catacombs);
    }

    public void play() {
      ArrayList<DomCard> theCards = owner.revealTopCards(3);
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman(theCards);
          return;
      }
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

    private void handleHuman(ArrayList<DomCard> theCards) {
        StringBuilder theStr = new StringBuilder();
        String thePrefix = "";
        for (DomCard theCard : theCards) {
            theStr.append(thePrefix).append(theCard.getName().toHTML());
            thePrefix=", ";
        }
        if (owner.getEngine().getGameFrame().askPlayer("<html>Discard " + theStr +"?</html>", "Resolving " + this.getName().toString())) {
            owner.discard(theCards);
            owner.drawCards(3);
        } else {
            owner.addCardsToHand(theCards);
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