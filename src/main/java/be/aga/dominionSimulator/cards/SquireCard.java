package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import javax.swing.*;

public class SquireCard extends DomCard {
    public SquireCard() {
      super( DomCardName.Squire);
    }

    public void play() {
        owner.addAvailableCoins(1);
        if ((owner.getActionsLeft()-owner.getCardsFromHand(DomCardType.Action).size()<1)&&!owner.getCardsFromHand(DomCardType.Action).isEmpty()) {
            owner.addActions(2);
            return;
        }
        if (owner.getPlayStrategyFor(this) == DomPlayStrategy.silverGainer) {
            owner.gain(DomCardName.Silver);
            return;
        }
        owner.addAvailableBuys(2);
    }

    @Override
    public void doWhenTrashed() {
        DomCardName theCard = owner.getDesiredCard(DomCardType.Attack, new DomCost(100, 1), false, false, null);
        if (theCard!=null)
          owner.gain(theCard);
    }

    @Override
    public int getPlayPriority() {
        if (owner.getActionsLeft()>1)
            return 32;
        if (owner.getCardsFromHand(DomCardType.Action).size()-owner.getCardsFromHand(DomCardName.Squire).size() > owner.getCardsFromHand(DomCardType.Terminal).size())
            return 32;
        if (owner.getCurrentGame().getBestCardInSupplyFor(owner,DomCardType.Attack,new DomCost(100,1),false)!=null
         &&owner.count(DomCardType.Attack)==0 && !owner.getCardsFromHand(DomCardName.Amulet).isEmpty())
            return 40;
        return super.getPlayPriority();
    }
}