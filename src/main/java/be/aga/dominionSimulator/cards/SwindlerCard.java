package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class SwindlerCard extends DomCard {
    public SwindlerCard () {
      super( DomCardName.Swindler);
    }

    public void play() {
      owner.addAvailableCoins(2);
      for (DomPlayer thePlayer : owner.getOpponents()) {
          if (thePlayer.checkDefense()) 
        	continue;
          ArrayList< DomCard > theCards = thePlayer.revealTopCards(1);
          if (theCards.isEmpty())
        	  continue;
          if (owner.isHumanOrPossessedByHuman()) {
              handleHuman(theCards, thePlayer);
              return;
          }
          DomCardName theNewCard = owner.getCurrentGame().getCardForSwindler(thePlayer, theCards.get(0).getCost(owner.getCurrentGame()));
          thePlayer.trash(theCards.get(0));
          if (theNewCard!=null)
            thePlayer.gain(theNewCard);
          else
        	if (DomEngine.haveToLog) DomEngine.addToLog(thePlayer + " gains nothing");
      }
    }

    private void handleHuman(ArrayList<DomCard> theCards, DomPlayer thePlayer) {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theCard.getCost(owner.getCurrentGame()).customCompare(theCards.get(0).getCost(owner.getCurrentGame()))==0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (!theChooseFrom.isEmpty()) {
            if (theChooseFrom.size() == 1)
                thePlayer.gain(owner.getCurrentGame().takeFromSupply(theChooseFrom.get(0)));
            else
                thePlayer.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for " + this.getName().toString(), theChooseFrom, "Mandatory!")));
        }
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}