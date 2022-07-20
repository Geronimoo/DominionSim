package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.enums.DomPhase;

import java.util.ArrayList;

public class WeaverCard extends DomCard {
    public WeaverCard() {
      super( DomCardName.Weaver);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom = new ArrayList<>();
            for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
                if (new DomCost(4,0).customCompare(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0 )
                    theChooseFrom.add(theCard);
            }
            if (theChooseFrom.isEmpty())
                return;
            DomCard cardToGain = owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for " + DomCardName.Workshop.toHTML(), theChooseFrom, "Mandatory!"));
            if (cardToGain.getName()==DomCardName.Silver)
                owner.gain(DomCardName.Silver);
            owner.gain(cardToGain);
        }else {
            DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(4, 0), false);
            if (theDesiredCard == null) {
                //possible to get here if card was throne-roomed
                theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(4, 0));
            }
            if (theDesiredCard != null) {
                if (theDesiredCard==DomCardName.Silver)
                    owner.gain(DomCardName.Silver);
                owner.gain(theDesiredCard);
            }
        }
    }

    @Override
    public void doWhenDiscarded() {
        if (owner.getCurrentGame().getActivePlayer().getPhase()!= DomPhase.CleanUp) {
            owner.play(this);
        } else {
            super.doWhenDiscarded();
        }
    }

    @Override
    public boolean wantsToBePlayed() {
       return owner.getDesiredCard(new DomCost( 4, 0), false) != null ;
    }

    @Override
    public int getPlayPriority() {
        if (owner.getDrawDeckSize()==0 && owner.getActionsAndVillagersLeft()>1 && !owner.getCardsFromHand(DomCardType.Cycler).isEmpty())
            return 1;
        return super.getPlayPriority();
    }
}