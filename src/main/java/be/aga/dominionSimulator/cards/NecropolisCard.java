package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class NecropolisCard extends DomCard {
    public NecropolisCard() {
      super( DomCardName.Necropolis);
    }

    public void play() {
      owner.addActions(2);
    }

    @Override
    public int getDiscardPriority(int aActionsLeft) {
        if (owner.getCardsFromHand(DomCardType.Action).size()>1)
            return 18;
        return 14;
    }
}