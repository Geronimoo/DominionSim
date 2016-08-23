package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Noble_BrigandCard extends DomCard {
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
            boolean treasureFound=false;
            ArrayList< DomCard > theCards = thePlayer.revealTopCards(2);
            DomCard theCardToTrash = null;
            for (DomCard theCard : theCards) {
			  if (theCard.hasCardType(DomCardType.Treasure))
            	treasureFound=true;
              if (theCard.getName()==DomCardName.Silver || theCard.getName()==DomCardName.Gold) {
                if (theCardToTrash==null 
                	|| theCard.getName().getTrashPriority(player)>theCardToTrash.getName().getTrashPriority(player)){
                  theCardToTrash = theCard;
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
}