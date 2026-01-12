package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class HorseCard extends DomCard {
    public HorseCard() {
      super( DomCardName.Horse);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(2);
      if (owner.getDeck().get(DomCardName.Horse).contains(this))
        owner.returnToSupply(owner.removeCardFromPlay(this));
    }

    @Override
    public boolean wantsToBePlayed() {
        return owner.getDeckAndDiscardSize()>0;
//        if (owner.isGoingToBuyTopCardInBuyRules(owner.getTotalPotentialCurrency()))
//            return false;
//        return true;
    }
}