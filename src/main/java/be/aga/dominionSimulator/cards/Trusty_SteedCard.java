package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Trusty_SteedCard extends DomCard {
    public Trusty_SteedCard () {
      super( DomCardName.Trusty_Steed);
    }

    public void play() {
      if (owner.getActionsLeft()>1) {
        owner.addAvailableCoins(2);
      } else {
    	owner.addActions(2);
      }
      owner.drawCards(2);
    }
}