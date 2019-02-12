package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class MessengerCard extends DomCard {
    public MessengerCard() {
      super( DomCardName.Messenger);
    }

    public void play() {
      owner.addAvailableCoins(2);
      owner.addAvailableBuys(1);
      if (owner.isHumanOrPossessedByHuman()) {
          if (owner.getEngine().getGameFrame().askPlayer("Put deck in discard?" , "Resolving " + this.getName().toString()))
              owner.putDeckInDiscard();
      } else {
          owner.putDeckInDiscard();
      }
    }
    @Override
    public int getPlayPriority() {
    	if (owner.getActionsLeft()>1)
    		return DomCardName.Counting_House.getPlayPriority()-1;
    	return super.getPlayPriority();
    }

    public void handleFirstBuy() {
        DomCardName theDesiredCard = null;
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
                if (new DomCost(4,0).customCompare(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0)
                    theChooseFrom.add(theCard);
            }
            if (theChooseFrom.isEmpty())
                return;
            theDesiredCard=owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!");
        } else {
            theDesiredCard = owner.getDesiredCard(new DomCost(4, 0), false);
            if (theDesiredCard == null)
                theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(4, 0));
        }
        if (theDesiredCard != null) {
            owner.gain(theDesiredCard);
            for (DomPlayer thePlayer : owner.getOpponents())
                thePlayer.gain(theDesiredCard);
        }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}