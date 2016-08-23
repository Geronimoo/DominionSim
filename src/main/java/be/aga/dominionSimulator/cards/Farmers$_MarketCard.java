package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class Farmers$_MarketCard extends DomCard {
    public Farmers$_MarketCard() {
      super( DomCardName.Farmers$_Market);
    }

    public void play() {
      owner.addAvailableBuys(1);
      if (owner.getCurrentGame().getBoard().countVPon(getName())>=4) {
          owner.addVP(owner.getCurrentGame().getBoard().countVPon(getName()));
          owner.getCurrentGame().getBoard().resetVPon(getName());
          owner.trash(owner.removeCardFromPlay(this));
      } else {
          owner.getCurrentGame().getBoard().addVPon(getName());
          owner.addAvailableCoins(owner.getCurrentGame().getBoard().countVPon(getName()));
      }
    }
}