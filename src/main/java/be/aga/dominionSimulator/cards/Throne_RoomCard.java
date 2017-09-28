package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Throne_RoomCard extends MultiplicationCard {
    public Throne_RoomCard () {
      super( DomCardName.Throne_Room);
    }

    @Override
    public int getDiscardPriority(int aActionsLeft) {
        if (owner.getCardsInHand().contains(this) && owner.getCardsFromHand(DomCardType.Action).size()==owner.getCardsFromHand(DomCardName.Throne_Room).size())
            return 1;
        return super.getDiscardPriority(aActionsLeft);
    }
}