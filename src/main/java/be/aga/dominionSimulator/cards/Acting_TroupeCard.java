package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class Acting_TroupeCard extends DomCard {
    public Acting_TroupeCard() {
      super( DomCardName.Acting_Troupe);
    }

    public void play() {
      owner.addVillagers(4);
      if (!owner.getCurrentGame().getBoard().getTrashedCards().contains(this)) {
          owner.removeCardFromPlay(this);
          owner.trash(this);
      } else {
          if (DomEngine.haveToLog) DomEngine.addToLog( owner + " already trashed " + this);
      }
    }
}