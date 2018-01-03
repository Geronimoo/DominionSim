package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;

public class Wild_HuntCard extends DomCard {
    public Wild_HuntCard() {
      super( DomCardName.Wild_Hunt);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        if (owner.getCurrentGame().countInSupply(DomCardName.Estate)==0 || (!owner.getCardsFromHand(DomCardName.Wild_Hunt).isEmpty() && owner.actionsLeft>0)) {
            drawCards();
            return;
        }
        if (owner.getPlayStrategyFor(this)== DomPlayStrategy.forEngines) {
            handleForEngines();
            return;
        }
        if ( owner.getCurrentGame().getBoard().countVPon(DomCardName.Wild_Hunt)>3
           || (owner.getCurrentGame().getBoard().countVPon(DomCardName.Wild_Hunt)>0 && owner.getCurrentGame().getGainsNeededToEndGame()<=4)){
            gainVP();
            return;
        }
        drawCards();
    }

    private void handleHuman() {
        ArrayList<String> theOptions = new ArrayList<String>();
        theOptions.add("Cards");
        theOptions.add("Points");
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Select for Wild Hunt", theOptions, "Mandatory!");
        if (theChoice == 0)
            drawCards();
        if (theChoice == 1)
            gainVP();
    }

    private void handleForEngines() {
        if (owner.getCurrentGame().getBoard().countVPon(DomCardName.Wild_Hunt)>5
        || (owner.getCurrentGame().getBoard().countVPon(DomCardName.Wild_Hunt)>0 && owner.getCurrentGame().getGainsNeededToEndGame()<=2)) {
            gainVP();
        } else {
            drawCards();
        }
    }

    private void gainVP() {
        if (owner.getCurrentGame().countInSupply(DomCardName.Estate)==0)
            return;
        owner.gain(DomCardName.Estate);
        owner.addVP(owner.getCurrentGame().getBoard().getAllVPFromPile(DomCardName.Wild_Hunt));
    }

    private void drawCards() {
        owner.drawCards(3);
        owner.getCurrentGame().getBoard().addVPon(DomCardName.Wild_Hunt );
    }
}