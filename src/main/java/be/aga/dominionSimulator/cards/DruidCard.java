package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class DruidCard extends DomCard {
    public DruidCard() {
      super( DomCardName.Druid);
    }

    public void play() {
      owner.addAvailableBuys(1);
      if (owner.isHumanOrPossessedByHuman()) {
          handleHuman();
      } else {
          owner.receiveBoon(owner.getCurrentGame().getBoard().getDruidBoons().get(0));
      }
    }

    private void handleHuman() {
        ArrayList<DomCard> theChooseFrom = owner.getCurrentGame().getBoard().getDruidBoons();
        owner.receiveBoon(owner.getEngine().getGameFrame().askToSelectOneCardWithDomCard("Receive a Boon", theChooseFrom, "Mandatory!"));
    }
}