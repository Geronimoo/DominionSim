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
            for (DomCard theCard : theCards) {
			  if (theCard.hasCardType(DomCardType.Treasure) && theCard.getName()!=DomCardName.Copper) {
                if (theCardToTrash==null
                || theCard.getName().getTrashPriority(owner)<theCardToTrash.getName().getTrashPriority(owner)){
                  theCardToTrash = theCard;
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
}