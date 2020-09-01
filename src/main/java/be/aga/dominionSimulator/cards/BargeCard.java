package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class BargeCard extends DomCard {
    private boolean durationFailed = false;

    public BargeCard() {
      super( DomCardName.Barge);
    }

    public void play() {
      durationFailed = true;
      if (owner.isHumanOrPossessedByHuman()) {
         handleHuman();
      } else {
          if (owner.getDeckAndDiscardSize()>=3) {
              if (owner.count(DomCardType.Village)>0 && owner.actionsLeft==0) {
                  durationFailed=false;
              } else {
                  owner.addAvailableBuys(1);
                  owner.drawCards(3);
              }
          } else {
              durationFailed = false;
          }
      }
    }

    private void handleHuman() {
        durationFailed = true;
        owner.setNeedsToUpdateGUI();
        if (owner.getEngine().getGameFrame().askPlayer("<html>Barge Now?</html>", "Resolving " + this.getName().toString())) {
            owner.addAvailableBuys(1);
            owner.drawCards(3);
        } else {
            durationFailed = false;
        }
    }

    public boolean durationFailed() {
        return durationFailed;
    }

    @Override
    public void resolveDuration() {
        owner.addAvailableBuys(1);
        owner.drawCards(3);
    }
}