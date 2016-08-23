package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

public class ArmoryCard extends DomCard {
    public ArmoryCard() {
      super( DomCardName.Armory);
    }

    public void play() {
        DomCardName theDesiredCard = owner.getDesiredCard(new DomCost( 4, 0), false);
        if (theDesiredCard==null) {
            //possible to get here if card was throne-roomed
            theDesiredCard=owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost(4, 0));
        }
        if (theDesiredCard!=null)
        owner.gainOnTopOfDeck(owner.getCurrentGame().takeFromSupply(theDesiredCard));
    }
    
    @Override
    public boolean wantsToBePlayed() {
       return owner.getDesiredCard(new DomCost( 4, 0), false) != null ;
    }
}