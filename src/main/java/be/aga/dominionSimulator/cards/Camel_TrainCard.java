package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Camel_TrainCard extends DomCard {
    public Camel_TrainCard() {
      super( DomCardName.Camel_Train);
    }

    public void play() {
        DomCardName thedesiredCard = owner.getDesiredCard(null, new DomCost(1000, 1000), false,false,DomCardType.Victory);
        if (thedesiredCard==null) {
            thedesiredCard=owner.getCurrentGame().getBoard().getBestCardInSupplyFor(owner, null, new DomCost(1000,1000), false, DomCardType.Victory, null );
        }
        if (thedesiredCard!=null) {
            DomCard aCard = owner.getCurrentGame().takeFromSupply(thedesiredCard);
            owner.getDeck().addPhysicalCardWhenNotGained(aCard);
            owner.moveToExileMat(aCard);
        }
    }

    @Override
    public void doWhenGained() {
        DomCard domCard = owner.getCurrentGame().takeFromSupply(DomCardName.Gold);
        if (domCard!=null) {
            owner.getDeck().addPhysicalCardWhenNotGained(domCard);
            owner.moveToExileMat(domCard);
        }
    }
}