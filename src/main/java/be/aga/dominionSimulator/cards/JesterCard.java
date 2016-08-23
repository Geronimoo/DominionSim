package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class JesterCard extends DomCard {
    public JesterCard () {
      super( DomCardName.Jester);
    }

    public void play() {
      owner.addAvailableCoins(2);
      for (DomPlayer thePlayer : owner.getOpponents()) {
          if (thePlayer.checkDefense()) 
        	continue;
          ArrayList< DomCard > theCards = thePlayer.revealTopCards(1);
          if (theCards.isEmpty())
        	continue;
          thePlayer.discard(theCards.get(0));
          if (theCards.get(0).hasCardType(DomCardType.Victory)){
        	  if (owner.getCurrentGame().countInSupply(DomCardName.Curse)>0)
        		thePlayer.gain(DomCardName.Curse);
        	  continue;
          }
          if (owner.getCurrentGame().countInSupply(theCards.get(0).getName())==0)
        	continue;
          if (theCards.get(0).getTrashPriority()<16) {
            thePlayer.gain(theCards.get(0).getName());
          } else {
            owner.gain(theCards.get(0).getName());
          }
      }
    }
}