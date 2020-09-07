package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;

import java.util.ArrayList;

public class Village_GreenCard extends DomCard {
    private boolean durationFailed = false;

    public Village_GreenCard() {
      super( DomCardName.Village_Green);
    }

    public void play() {
      durationFailed = true;
      if (owner.isHumanOrPossessedByHuman()) {
         handleHuman();
      } else {
          if ((owner.getDeckAndDiscardSize()>=1 || (owner.count(DomCardType.Terminal)-owner.countInPlay(DomCardType.Terminal))>owner.actionsLeft) && (owner.getCurrentGame().getActivePlayer()==owner)) {
        	  owner.addActions(2);;
              owner.drawCards(1);
          }
          else {
              durationFailed = false;
          }
      }
    }

    private void handleHuman() {
        durationFailed = true;
        owner.setNeedsToUpdateGUI();
        if (owner.getEngine().getGameFrame().askPlayer("<html>Barge Now?</html>", "Resolving " + this.getName().toString())) {
        	owner.addActions(2);;
            owner.drawCards(1);
        } else {
            durationFailed = false;
        }
    }

    public boolean durationFailed() {
        return durationFailed;
    }

    @Override
    public void resolveDuration() {
    	owner.addActions(2);;
        owner.drawCards(1);
    }
    
    @Override
    public void doWhenDiscarded() {
    	if (owner.getCurrentGame().getActivePlayer().getPhase()!=DomPhase.CleanUp) {
    	    if (owner.isHumanOrPossessedByHuman()) {
                if (owner.getEngine().getGameFrame().askPlayer("<html>Play " + DomCardName.Village_Green.toHTML() +" ?</html>", "Resolving " + this.getName().toString()))
                	owner.play(this);
            } else {
            	owner.play(this);
            }
        }
    }
}