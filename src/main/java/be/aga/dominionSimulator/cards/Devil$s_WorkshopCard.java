package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class Devil$s_WorkshopCard extends DomCard {
    public Devil$s_WorkshopCard() {
      super( DomCardName.Devil$s_Workshop);
    }

    public void play() {
        if (owner.getCardsGainedLastTurn().isEmpty()) {
            owner.gain(DomCardName.Gold);
        } else {
            if (owner.getCardsGainedLastTurn().size() > 1) {
                owner.gain(DomCardName.Imp);
            } else {
                if (owner.isHumanOrPossessedByHuman()) {
                    handleHumanPlayer();
                } else {
                    DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(4, 0), false);
                    if (theDesiredCard == null) {
                        //possible to get here if card was throne-roomed
                        theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(4, 0));
                    }
                    if (theDesiredCard != null)
                        owner.gain(theDesiredCard);
                }
            }
        }
    }

    private void handleHumanPlayer() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().keySet()) {
            if (new DomCost(4,0).compareTo(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0 )
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gain(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!")));
    }

    @Override
    public boolean wantsToBePlayed() {
        if (owner.getCardsGainedLastTurn().size() == 1)
          return owner.getDesiredCard(new DomCost( 4, 0), false) != null ;
        return true;
    }

    @Override
    public int getPlayPriority() {
        if (owner.getDrawDeckSize()==0 && owner.getActionsLeft()>1 && !owner.getCardsFromHand(DomCardType.Cycler).isEmpty())
            return 1;
        return super.getPlayPriority();
    }
}