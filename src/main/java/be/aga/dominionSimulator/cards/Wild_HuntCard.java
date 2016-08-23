package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.Collections;
import java.util.HashSet;

public class Wild_HuntCard extends DomCard {
    public Wild_HuntCard() {
      super( DomCardName.Wild_Hunt);
    }

    public void play() {
        if (owner.getCurrentGame().countInSupply(DomCardName.Estate)==0 && owner.getDeckSize()>0) {
            drawCards();
            return;
        }
        if (owner.getPlayStrategyFor(this)== DomPlayStrategy.forEngines) {
            if (owner.getCurrentGame().getBoard().countVPon(DomCardName.Wild_Hunt)>5
            || (owner.getCurrentGame().getBoard().countVPon(DomCardName.Wild_Hunt)>0 && owner.getCurrentGame().getGainsNeededToEndGame()<=2)) {
                gainVP();
                return;
            } else {
                drawCards();
                return;
            }
        }
        if ( owner.getCurrentGame().getBoard().countVPon(DomCardName.Wild_Hunt)>3
           || (owner.getCurrentGame().getBoard().countVPon(DomCardName.Wild_Hunt)>0 && owner.getCurrentGame().getGainsNeededToEndGame()<=4)){
            gainVP();
            return;
        }
        drawCards();
    }

    private void gainVP() {
        owner.gain(DomCardName.Estate);
        owner.addVP(owner.getCurrentGame().getBoard().getAllVPFromPile(DomCardName.Wild_Hunt));
    }

    private void drawCards() {
        owner.drawCards(3);
        owner.getCurrentGame().getBoard().addVPon(DomCardName.Wild_Hunt );
    }
}