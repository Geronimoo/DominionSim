package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Fortune_TellerCard extends DomCard {
    public Fortune_TellerCard () {
      super( DomCardName.Fortune_Teller);
    }

    @Override
    public void play() {
    	owner.addAvailableCoins(2);
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (thePlayer.checkDefense()) 
            	continue;
            ArrayList< DomCard > theRevealedCards = thePlayer.revealUntilVictoryOrCurse();
            for (DomCard theCard : theRevealedCards) {
                if (theCard.hasCardType( DomCardType.Victory ) || theCard.hasCardType(DomCardType.Curse)){
                	thePlayer.putOnTopOfDeck(theCard);
                }else{
                	thePlayer.discard(theCard);
                }
            }
        }
    }
}