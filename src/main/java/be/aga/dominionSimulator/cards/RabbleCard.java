package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class RabbleCard extends DomCard {
    public RabbleCard () {
      super( DomCardName.Rabble);
    }

    @Override
    public void play() {
    	owner.drawCards(3);
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (thePlayer.checkDefense())
            	continue;
            ArrayList< DomCard > theTopThree = thePlayer.revealTopCards(3);
            if (thePlayer.isHumanOrPossessedByHuman()) {
                handleHuman(thePlayer,theTopThree);
            }  else {
                for (DomCard theCard : theTopThree) {
                    if (theCard.hasCardType(DomCardType.Treasure) || theCard.hasCardType(DomCardType.Action)) {
                        thePlayer.discard(theCard);
                    } else {
                        thePlayer.putOnTopOfDeck(theCard);
                    }
                }
            }
        }
    }

    private void handleHuman(DomPlayer thePlayer, ArrayList<DomCard> theTopThree) {
        ArrayList<DomCard> theVP = new ArrayList<DomCard>();
        for (DomCard theCard : theTopThree)
            if (!theCard.hasCardType(DomCardType.Treasure) && !theCard.hasCardType(DomCardType.Action) )
                theVP.add(theCard);
        if (theVP.size()==1) {
            thePlayer.putOnTopOfDeck(theVP.get(0));
            return;
        }
        ArrayList<DomCard> theChosenCards = new ArrayList<DomCard>();;
        owner.getEngine().getGameFrame().askToSelectCards("<html>Choose <u>order</u> (first card = top card)</html>" , theVP, theChosenCards, theVP.size());
        for (int i=theChosenCards.size()-1;i>=0;i--) {
            for (DomCard theCard : theVP) {
                if (theChosenCards.get(i).getName()==theCard.getName()) {
                    thePlayer.putOnTopOfDeck(theCard);
                    theVP.remove(theCard);
                    break;
                }
            }
        }
    }

    public int getPlayPriority() {
      if (owner.getDeckAndDiscardSize() == 0)
          return 50;
      return owner.getActionsAndVillagersLeft() > 1 && owner.getDeckAndDiscardSize()>0 ? 6 : super.getPlayPriority();
    }

    @Override
    public int getDiscardPriority(int aActionsLeft) {
        if (owner.getDeckAndDiscardSize() == 0)
          return 15;
        else
          return 35;
    }
}