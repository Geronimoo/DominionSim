package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class ThiefCard extends DomCard {
    public ThiefCard () {
      super( DomCardName.Thief);
    }

    public void play() {
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (thePlayer.checkDefense())
            	continue;
            ArrayList< DomCard > theCards = thePlayer.revealTopCards(2);
            DomCard theCardToTrash = null;
            for (DomCard theCard : theCards) {
              if (!theCard.hasCardType(DomCardType.Treasure))
            	  continue;
              if (theCardToTrash==null 
               || theCard.getName().getTrashPriority(owner)>theCardToTrash.getName().getTrashPriority(owner)
               || (theCard.getName()==DomCardName.Ill_Gotten_Gains && owner.wantsToGainOrKeep(DomCardName.Ill_Gotten_Gains))){ 
                theCardToTrash = theCard;
              }
            }
            if (theCardToTrash!=null) {
              thePlayer.trash( theCardToTrash );
              if (owner.wantsToGainOrKeep(theCardToTrash.getName())) {
                owner.gain(owner.getCurrentGame().removeFromTrash(theCardToTrash));
              }
              theCards.remove( theCardToTrash );
            }
            thePlayer.discard( theCards);
          }
    }
}