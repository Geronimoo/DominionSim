package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class CreditCard extends DomCard {
    public CreditCard() {
      super( DomCardName.Credit);
    }

    public void play() {
        DomCardName theDesiredCard = owner.getDesiredCard(null,new DomCost(8, 0), false, false, DomCardType.Victory);
        if (theDesiredCard.hasCardType(DomCardType.Treasure) || theDesiredCard.hasCardType(DomCardType.Action)) {
            owner.gain(theDesiredCard);
            owner.addDebt(theDesiredCard.getCoinCost(owner));
        }
    }
}