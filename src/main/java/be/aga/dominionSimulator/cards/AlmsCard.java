package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class AlmsCard extends DomCard {

	public AlmsCard() {
      super( DomCardName.Alms);
    }

    public void play() {
        owner.setAlmsActivated();
        if (owner.countInPlay(DomCardType.Treasure)>0)
            return;
        DomCardName theDesiredCard = owner.getDesiredCard(new DomCost(4, 0), false);
        if (theDesiredCard!=null)
            owner.gain(theDesiredCard);
    }
}