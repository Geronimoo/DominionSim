package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class ArmoryCard extends DomCard {
    public ArmoryCard() {
      super( DomCardName.Armory);
    }

    public void play() {
        if (owner.isHumanOrPossessedByHuman()) {
            handleHumanPlayer();
            return;
        }
        DomCardName theDesiredCard = owner.getDesiredCard(new DomCost( 4, 0), false);
        if (theDesiredCard==null) {
            //possible to get here if card was throne-roomed
            theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(4, 0));
        }
        if (theDesiredCard!=null)
        owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(theDesiredCard));
    }

    private void handleHumanPlayer() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (new DomCost(4,0).customCompare(theCard.getCost(owner.getCurrentGame()))>=0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (theChooseFrom.isEmpty())
            return;
        owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(owner.getEngine().getGameFrame().askToSelectOneCard("Select card to gain for "+this.getName().toString(), theChooseFrom, "Mandatory!")));
    }

    @Override
    public boolean wantsToBePlayed() {
       return owner.getDesiredCard(new DomCost( 4, 0), false) != null ;
    }
}