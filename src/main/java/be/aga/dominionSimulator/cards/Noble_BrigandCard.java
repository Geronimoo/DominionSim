package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Noble_BrigandCard extends DomCard {
    private boolean treasureFound;

    public Noble_BrigandCard () {
      super( DomCardName.Noble_Brigand);
    }

    public void play() {
    	owner.addAvailableCoins(1);
    	attack(owner);
    }
    
	public void attack(DomPlayer player) {
		//player is sometimes not owner!!! if player is possessed and buys this card he needs to attack the other players
        for (DomPlayer thePlayer : player.getOpponents()) {
            if (thePlayer.checkDefense())
            	continue;
            treasureFound=false;
            ArrayList< DomCard > theCards = thePlayer.revealTopCards(2);
            DomCard theCardToTrash = null;
            if (owner.isHumanOrPossessedByHuman()) {
                theCardToTrash=handleHuman(theCards);
            } else {
                for (DomCard theCard : theCards) {
                    if (theCard.hasCardType(DomCardType.Treasure))
                        treasureFound = true;
                    if (theCard.getName() == DomCardName.Silver || theCard.getName() == DomCardName.Gold) {
                        if (theCardToTrash == null
                                || theCard.getName().getTrashPriority(player) > theCardToTrash.getName().getTrashPriority(player)) {
                            theCardToTrash = theCard;
                        }
                    }
                }
            }
            if (theCardToTrash!=null) {
              thePlayer.trash( theCardToTrash );
              player.gain(player.getCurrentGame().removeFromTrash(theCardToTrash));
              theCards.remove( theCardToTrash );
            }
            thePlayer.discard( theCards);
            if (!treasureFound && player.getCurrentGame().countInSupply(DomCardName.Copper)>0)
            	thePlayer.gain(DomCardName.Copper);
          }
	}

    private DomCard handleHuman(ArrayList<DomCard> theCards) {
        ArrayList<DomCardName> theChoosefrom = new ArrayList<DomCardName>();
        for (DomCard theCard : theCards) {
            if (theCard.hasCardType(DomCardType.Treasure))
                treasureFound = true;
            if (theCard.getName() == DomCardName.Silver || theCard.getName() == DomCardName.Gold) {
                theChoosefrom.add(theCard.getName());
            }
        }
        if (theChoosefrom.isEmpty())
            return null;
        if (theChoosefrom.size()==1) {
            for (DomCard theCard : theCards)
                if (theCard.getName() == theChoosefrom.get(0))
                    return theCard;
        }
        DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChoosefrom, "Mandatory!");
        for (DomCard theCard : theCards)
            if (theCard.getName() == theChosenCard)
                return theCard;
        return null;
    }
}