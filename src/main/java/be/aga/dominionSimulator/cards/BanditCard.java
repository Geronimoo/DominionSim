package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class BanditCard extends DomCard {
    public BanditCard() {
      super( DomCardName.Bandit);
    }

    public void play() {
    	owner.gain(DomCardName.Gold);
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (thePlayer.checkDefense())
            	continue;
            ArrayList< DomCard > theCards = thePlayer.revealTopCards(2);
            DomCard theCardToTrash = null;
            ArrayList<DomCard> theTreasures = new ArrayList<DomCard>();
            for (DomCard theCard : theCards) {
                if (theCard.hasCardType(DomCardType.Treasure) && theCard.getName()!=DomCardName.Copper)
                    theTreasures.add(theCard);
            }
            if (theTreasures.isEmpty()) {
                thePlayer.discard(theCards);
                continue;
            }
            if (thePlayer.isHuman()) {
                theCardToTrash = handleHumanOpponent(theCardToTrash, theTreasures);
            } else {
                for (DomCard theCard : theCards) {
                    if (theCard.hasCardType(DomCardType.Treasure) && theCard.getName() != DomCardName.Copper) {
                        if (theCardToTrash == null
                                || theCard.getName().getTrashPriority(owner) < theCardToTrash.getName().getTrashPriority(owner)) {
                            theCardToTrash = theCard;
                        }
                    }
                }
            }
            if (theCardToTrash!=null) {
              thePlayer.trash( theCardToTrash );
              theCards.remove( theCardToTrash );
            }
            thePlayer.discard( theCards);
          }
    }

    private DomCard handleHumanOpponent(DomCard theCardToTrash, ArrayList<DomCard> theTreasures) {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCard theCard : theTreasures)
          if (theCard.hasCardType(DomCardType.Treasure) && theCard.getName() != DomCardName.Copper)
            theChooseFrom.add(theCard.getName());
        if (!theChooseFrom.isEmpty()) {
            DomCardName theChosenCard = theChooseFrom.get(0);
            if (theChooseFrom.size()>1) {
              theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Select card for " + this.getName().toString(), theChooseFrom, "Mandatory!");
            }
            for (DomCard theCard : theTreasures)
                if (theCard.getName() == theChosenCard)
                    theCardToTrash = theCard;
        }
        return theCardToTrash;
    }
}