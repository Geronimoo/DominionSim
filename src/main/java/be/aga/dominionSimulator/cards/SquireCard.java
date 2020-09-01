package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

import java.util.ArrayList;

public class SquireCard extends DomCard {
    public SquireCard() {
      super( DomCardName.Squire);
    }

    public void play() {
        owner.addAvailableCoins(1);
        if (owner.isHumanOrPossessedByHuman()) {
            handleHuman();
            return;
        }
        if ((owner.getActionsAndVillagersLeft()-owner.getCardsFromHand(DomCardType.Action).size()<1)&&!owner.getCardsFromHand(DomCardType.Action).isEmpty()) {
            owner.addActions(2);
            return;
        }
        if (owner.getPlayStrategyFor(this) == DomPlayStrategy.silverGainer) {
            owner.gain(DomCardName.Silver);
            return;
        }
        owner.addAvailableBuys(2);
    }

    private void handleHuman() {
        ArrayList<String> theOptions = new ArrayList<String>();
        theOptions.add("+2 Actions");
        theOptions.add("+2 Buys");
        theOptions.add("Gain a Silver");
        int theChoice = owner.getEngine().getGameFrame().askToSelectOption("Select for Squire", theOptions, "Mandatory!");
        if (theChoice == 0)
            owner.addActions(2);
        if (theChoice == 1)
            owner.addAvailableBuys(2);
        if (theChoice == 2)
            owner.gain(DomCardName.Silver);
    }

    @Override
    public void doWhenTrashed() {
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
            for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
                if (theCard.hasCardType(DomCardType.Attack) && theCard.hasCardType(DomCardType.Action) && owner.getCurrentGame().countInSupply(theCard)>0)
                    theChooseFrom.add(theCard);
            }
            if (theChooseFrom.isEmpty())
                return;
            owner.gain(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain from "+this.getName().toString(), theChooseFrom, "Mandatory!"));
        } else {
            DomCardName theCard = owner.getDesiredCard(DomCardType.Attack, new DomCost(100, 1), false, false, null);
            if (theCard != null)
                owner.gain(theCard);
        }
    }

    @Override
    public int getPlayPriority() {
        if (owner.getActionsAndVillagersLeft()>1)
            return 32;
//        if (owner.getCardsFromHand(DomCardType.Action).size()-owner.getCardsFromHand(DomCardName.Squire).size() > owner.getCardsFromHand(DomCardType.Terminal).size())
//            return 32;
        if (owner.getCurrentGame().getBestCardInSupplyFor(owner,DomCardType.Attack,new DomCost(100,1),false)!=null
         &&owner.count(DomCardType.Attack)==0 && !owner.getCardsFromHand(DomCardName.Amulet).isEmpty())
            return 40;
        return super.getPlayPriority();
    }

    @Override
    public boolean hasCardType(DomCardType aType) {
        if (aType==DomCardType.Treasure && owner != null && owner.hasBuiltProject(DomCardName.Capitalism))
            return true;
        return super.hasCardType(aType);
    }

}