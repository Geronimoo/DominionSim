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
            for (DomCard theCard : theTopThree) {
                if (theCard.hasCardType( DomCardType.Treasure ) || theCard.hasCardType(DomCardType.Action)){
                	thePlayer.discard(theCard);
                }else{
                	thePlayer.putOnTopOfDeck(theCard);
                }
            }
        }
    }
    
    public int getPlayPriority() {
      if (owner.getDeckSize() == 0)
          return 50;
      return owner.getActionsLeft() > 1 && owner.getDeckSize()>0 ? 6 : super.getPlayPriority();
    }

    @Override
    public int getDiscardPriority(int aActionsLeft) {
        if (owner.getDeckSize() == 0)
          return 15;
        else
          return 35;
    }
}