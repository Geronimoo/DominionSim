package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;
import java.util.Collections;

public class ScavengerCard extends DomCard {
    public ScavengerCard() {
      super( DomCardName.Scavenger);
    }

    public void play() {
      owner.addAvailableCoins(2);
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
          return;
      }
      owner.putDeckInDiscard();
      ArrayList<DomCard> theCardsToConsider = owner.getCardsFromDiscard();
      if (theCardsToConsider.isEmpty())
          return;
      Collections.sort(theCardsToConsider, SORT_FOR_DISCARDING);
      DomCard theChosenCard = theCardsToConsider.get(theCardsToConsider.size() - 1);
      if (owner.count(DomCardName.Stash)>0) {
          for (DomCard theCard : theCardsToConsider){
              if (theCard.getName()==DomCardName.Scavenger)
                  theChosenCard=theCard;
          }
      }
      owner.putOnTopOfDeck(owner.removeCardFromDiscard(theChosenCard));
    }

    private void handleHuman() {
        owner.setNeedsToUpdateGUI();
        if (owner.getEngine().getGameFrame().askPlayer("<html>Put deck in discard ? </html>", "Resolving " + this.getName().toString()))
          owner.putDeckInDiscard();
        owner.setNeedsToUpdateGUI();
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : owner.getCardsFromDiscard())
            theChooseFrom.add(theCard.getName());
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Put on top of deck" + this.getName().toString(), theChooseFrom, "Mandatory!");
        for (DomCard theCard:owner.getCardsFromDiscard()) {
            if (theCard.getName()==theChosenCard) {
                owner.putOnTopOfDeck(owner.removeCardFromDiscard(theCard));
                break;
            }
        }
    }

    @Override
    public int getPlayPriority() {
    	if (owner.getActionsLeft()>1)
    		return DomCardName.Counting_House.getPlayPriority()-1;
    	return super.getPlayPriority();
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}