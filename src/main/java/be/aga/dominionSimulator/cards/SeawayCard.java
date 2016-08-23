package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class SeawayCard extends DomCard {

	public SeawayCard() {
      super( DomCardName.Seaway);
    }

    public void play() {
        DomCardName theDesiredCard = owner.getDesiredCard(DomCardType.Action,new DomCost(4, 0), false,false,null);
        if (theDesiredCard!=null)
            owner.gain(theDesiredCard);
        owner.setPlusBuyAction(theDesiredCard);
   }
}