package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class HerbalistCard extends DomCard {
    public HerbalistCard () {
      super( DomCardName.Herbalist);
    }

    public void play() {
      owner.addAvailableCoins(1);
      owner.addAvailableBuys(1);
    }
    
	public void maybeAddTagFor(ArrayList<DomCard> theCardsToHandle) {
        //if due to Capitalism Herbalist has been tagged itself by another Herbalist it can't tag another card
      if (isTaggedByHerbalist())
          return;
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman(theCardsToHandle);
          return;
      }
      if (!owner.getCardsFromPlay(DomCardName.Capital).isEmpty() && !owner.getCardsFromPlay(DomCardName.Capital).get(0).isTaggedByHerbalist()) {
          owner.getCardsFromPlay(DomCardName.Capital).get(0).addHerbalistTag();
          return;
      }
      for (int i=theCardsToHandle.size()-1;i>=0;i--) {
    	DomCard theCard = theCardsToHandle.get(i);
    	if (theCard!=this
         && !theCard.isTaggedByHerbalist()
    	 && theCard.getDiscardPriority(1)> DomCardName.Silver.getDiscardPriority(1)
                && theCard.hasCardType(DomCardType.Treasure) ) {
    	    if (theCard.getName()==DomCardName.Herbalist) {
    	        if (i>theCardsToHandle.indexOf(this)) {
                    theCard.addHerbalistTag();
                    break;
                }
            } else {
                theCard.addHerbalistTag();
                break;
            }
    	}
      }
	}

    private void handleHuman(ArrayList<DomCard> theCardsToHandle) {
        ArrayList<DomCardName> chooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : theCardsToHandle) {
            if (theCard.hasCardType(DomCardType.Treasure) && !theCard.isTaggedByHerbalist() && theCard!=this)
                chooseFrom.add(theCard.getName());
        }
        if (chooseFrom.isEmpty())
            return;
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Put on top?" + this.getName().toString(), chooseFrom, "Don't put anything on top");
        if (theChosenCard!=null) {
            for (DomCard theCard : theCardsToHandle) {
                if (theCard.hasCardType(DomCardType.Treasure) && !theCard.isTaggedByHerbalist() && theCard.getName()==theChosenCard) {
                    theCard.addHerbalistTag();
                    return;
                }
            }
        }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }
}