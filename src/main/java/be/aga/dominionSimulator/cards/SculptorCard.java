package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.ArrayList;

public class SculptorCard extends DomCard {
    public SculptorCard() {
      super( DomCardName.Sculptor);
    }

    public void play() {
        DomCardName theDesiredCard = null;
        if (owner.isHumanOrPossessedByHuman()) {
            theDesiredCard = handleHumanPlayer();
        } else {
            if (owner.getActionsAndVillagersLeft()==0 && owner.getNextActionToPlay()!=null) {
                theDesiredCard = owner.getDesiredCard(DomCardType.Treasure, new DomCost(4, 0), false,false,null);
            }
            if (theDesiredCard==null)
                theDesiredCard = owner.getDesiredCard(new DomCost(4, 0), false);
            if (theDesiredCard == null) {
                //possible to get here if card was throne-roomed
                theDesiredCard = owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(4, 0));
            }
        }
        if (theDesiredCard != null && owner.getCurrentGame().countInSupply(theDesiredCard) > 0 ) {
            DomCard theCard = owner.getCurrentGame().takeFromSupply(theDesiredCard);
            owner.gainInHand(theCard);
            if (theCard.hasCardType(DomCardType.Treasure))
                owner.addVillagers(1);
        }
    }

    private DomCardName handleHumanPlayer() {
        ArrayList<DomCardName> theChooseFrom = new ArrayList<DomCardName>();
        for (DomCardName theCard : owner.getCurrentGame().getBoard().getTopCardsOfPiles()) {
            if (theCard.getCost(owner.getCurrentGame()).customCompare(new DomCost(4, 0)) <= 0 && owner.getCurrentGame().countInSupply(theCard)>0)
                theChooseFrom.add(theCard);
        }
        if (!theChooseFrom.isEmpty()) {
          return owner.getEngine().getGameFrame().askToSelectOneCard("Gain a card for" + this.getName().toString(), theChooseFrom, "Mandatory!");
        }
        return null;
    }

    @Override
    public boolean wantsToBePlayed() {
       return owner.getDesiredCard(new DomCost( 4, 0), false) != null ;
    }
}