package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class Trusty_SteedCard extends DomCard {
    public Trusty_SteedCard () {
      super( DomCardName.Trusty_Steed);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
      if (owner.getActionsLeft()>1) {
        owner.addAvailableCoins(2);
      } else {
    	owner.addActions(2);
      }
      owner.drawCards(2);
    }

    private void handleHuman() {
        ArrayList<String> theOptions = new ArrayList<String>();
        theOptions.add("+2 Actions/+2 Cards");
        theOptions.add("+2 Actions/+$2");
        theOptions.add("+2 Actions/+4 Silvers");
        theOptions.add("+2 Cards/+4 Silvers");
        theOptions.add("+2 Cards/+$2");
        theOptions.add("+$2/+$4 Silvers");
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Select for Trusty Steed", theOptions, "Mandatory!");
        if (theChoice==0) {
            owner.addActions(2);
            owner.drawCards(2);
        }
        if (theChoice==1) {
            owner.addActions(2);
            owner.addAvailableCoins(2);
        }
        if (theChoice==2) {
            owner.addActions(2);
            for (int i=0;i<4;i++)
                owner.gain(DomCardName.Silver);
            owner.putDeckInDiscard();
        }
        if (theChoice==3) {
            for (int i=0;i<4;i++)
                owner.gain(DomCardName.Silver);
            owner.putDeckInDiscard();
            owner.drawCards(2);
        }
        if (theChoice==4) {
            owner.addAvailableCoins(2);
            owner.drawCards(2);
        }
        if (theChoice==5) {
            owner.addAvailableCoins(2);
            for (int i=0;i<4;i++)
                owner.gain(DomCardName.Silver);
            owner.putDeckInDiscard();
        }
   }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}