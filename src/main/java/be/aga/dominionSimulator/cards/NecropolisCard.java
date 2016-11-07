package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

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

    @Override
    public int getTrashPriority() {
        if (owner.getPlayStrategyFor(this)==DomPlayStrategy.trashWhenObsolete)
            return DomCardName.Copper.getTrashPriority()-1;
        return super.getTrashPriority();
    }

    @Override
    public int getPlayPriority() {
        if (owner.getPlayStrategyFor(this)==DomPlayStrategy.trashWhenObsolete && !owner.getCardsFromHand(DomCardType.Trasher).isEmpty())
            return 50;
        return super.getPlayPriority();
    }
}