package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class VampireCard extends DomCard {
    public VampireCard() {
      super( DomCardName.Vampire);
    }

    public void play() {
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (!thePlayer.checkDefense()) {
                thePlayer.receiveHex(null);
            }
        }
        if (owner.isHumanOrPossessedByHuman()) {
            handleHumanPlayer();
        }else {
            DomCardName theDesiredCard = owner.getDesiredCardWithRestriction(null,new DomCost(5, 0), false, DomCardName.Vampire);
            if (theDesiredCard == null) {
                //possible to get here if card was throne-roomed
                theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(5, 0));
            }
            if (theDesiredCard != null)
                owner.gain(theDesiredCard);
        }
    }

    private void handleHumanPlayer() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (new DomCost(5,0).customCompare(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0 )
                if (theCard!=DomCardName.Vampire)
                  theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!")));

    }

    @Override
    public void handleCleanUpPhase() {
       DomPlayer theOwner = owner;
       DomCard theBat = owner.getCurrentGame().takeFromSupply(DomCardName.Bat);
       if (theBat==null)
           return;
       owner.returnToSupply(this);
       theOwner.getDeck().addPhysicalCardWhenNotGained(theBat);
       theOwner.getDeck().justAddToDiscard(theBat);
    }
}